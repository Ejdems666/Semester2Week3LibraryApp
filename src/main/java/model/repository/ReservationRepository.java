package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.MaterialType;
import model.entity.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by adam on 26/02/2017.
 */
public class ReservationRepository implements Repository<Reservation> {
    private final HyggeDb db;
    private static Repository<Reservation> instance;

    private Collection<Reservation> persistedEntities = new ArrayList<>();

    public static Repository<Reservation> getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new ReservationRepository(db);
        } else {
            return instance;
        }
    }
    private ReservationRepository(HyggeDb db) {
        this.db = db;
        instance = this;
    }

    @Override
    public Reservation getById(int id) {
        return null;
    }
    private Reservation mapReservation(ResultSet rs) {
        Reservation reservation = null;
        try {
            if (rs.next()) {
                Repository<MaterialType> materialTypeRepository = MaterialTypeRepository.getInstance(db);
                reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setReservedAt(rs.getDate("reserved_at"));
                reservation.setReservedUntil(rs.getDate("reserved_until"));
                reservation.setStatus(rs.getInt("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public List<Reservation> findBy(Condition condition) {
        Selection selection = new Selection("reservation");
        selection.setWhere(condition);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapMaterials(rs);
    }
    private List<Reservation> mapMaterials(ResultSet rs) {
        List<Reservation> materials = new ArrayList<>();
        Reservation reservation = mapReservation(rs);
        while (reservation != null) {
            materials.add(reservation);
            reservation = mapReservation(rs);
        }
        return materials;
    }

    public void persist(Reservation entity) {
        persistedEntities.add(entity);
    }

    public void persistAndFlush(Reservation entity) {
        persist(entity);
        flush();
    }

    public void flush() {
        Object[] objects;
        String sql;
        for (Reservation reservation : persistedEntities) {
            sql = "`reservation`(`user_id`, `reserved_at`, `reserved_until`, `status`, `material_id`) VALUES (?,?,?,?,?)";
            objects = new Object[5];
            objects[0] = reservation.getUser();
            objects[1] = reservation.getReservedAt();
            objects[2] = reservation.getReservedUntil();
            objects[3] = reservation.getStatus();
            objects[4] = reservation.getMaterial();
            int id = db.getInsertionExecutor().insert(sql, objects);
            reservation.setId(id);
        }
    }
}
