package controller;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import model.Connector;
import model.entity.Material;
import model.entity.Reservation;
import model.repository.MaterialRepository;
import model.repository.Repository;
import model.repository.ReservationRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Created by adam on 26/02/2017.
 */
public class MaterialController extends BaseController {
    private HyggeDb db;
    private Repository<Material> materialRepository;

    public MaterialController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        db = new HyggeDb(new Connector());
        materialRepository = MaterialRepository.getInstance(db);
    }

    public void index() {
        Integer materialId = parseMaterialId();
        if (materialId == null) {
            showAll();
        } else {
            showDetail(materialId);
        }
    }

    private Integer parseMaterialId() {
        Integer materialId = null;
        String rawBookId = request.getParameter("id");
        if (rawBookId != null) {
            try {
                materialId = Integer.parseInt(rawBookId);
            } catch (NumberFormatException e) {
            }
        }
        return materialId;
    }

    private void showAll() {
        Collection<Material> materials = materialRepository.findAll();
        request.setAttribute("materials", materials);
        renderTemplate("material/all");
    }

    private void showDetail(Integer materialId) {
        if (isLoggedIn()) {
            Repository<Reservation> reservationRepository = ReservationRepository.getInstance(db);
            Condition condition = new Condition("", "reservation", "user_id=?", user.getId()).and("material_id=?", Reservation.NOT_RETURNED).and("status=?", Reservation.NOT_RETURNED);
            List<Reservation> reservations = reservationRepository.findBy(condition);
            if (!reservations.isEmpty()) {
                request.setAttribute("reservation",reservations.get(reservations.size()-1));
            }
        }
        Material material = materialRepository.getById(materialId);
        if (material == null) {
            renderTemplate("404");
        } else {
            request.setAttribute("material", material);
            renderTemplate("material/detail");
        }
    }

    public void reserve() {
        Integer materialId = parseMaterialId();
        if (isLoggedIn()) {
            if (materialId != null) {
                Repository<Reservation> reservationRepository = ReservationRepository.getInstance(db);
                if (request.getMethod().equals("POST")) {
                    processReservation(materialId, reservationRepository);
                }
            }
        }
        redirect(ROOT+"material?id="+materialId);
    }
    private void processReservation(Integer materialId, Repository<Reservation> reservationRepository) {
        Condition condition = new Condition("", "reservation", "user_id=?", user.getId())
                .and("status=?", Reservation.NOT_RETURNED);
        List<Reservation> reservations = reservationRepository.findBy(condition);
        if (reservations.size() >= 10) {
            setAlert("danger","You're currently have more than 10 materials reserved!");
        } else {
            condition.and("material_id=?",materialId);
            reservations = reservationRepository.findBy(condition);
            if (!reservations.isEmpty()) {
                setAlert("danger","You're currently have this material reserved!");
            } else {
                Reservation reservation = new Reservation();
                reservation.setStatus(Reservation.NOT_RETURNED);
                Date date = new Date(Calendar.getInstance().getTimeInMillis());
                reservation.setReservedAt(date);
                reservation.setReservedUntil(date);
                reservation.setUser(user.getId());
                reservation.setUser(materialId);
                reservationRepository.persistAndFlush(reservation);
                setAlert("success","material-reserved");
            }
        }
    }


}
