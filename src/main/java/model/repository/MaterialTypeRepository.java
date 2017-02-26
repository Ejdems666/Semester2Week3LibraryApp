package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.MaterialType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 26/02/2017.
 */
public class MaterialTypeRepository implements Repository<MaterialType> {
    private final HyggeDb db;
    private static Repository<MaterialType> instance;

    public static Repository<MaterialType> getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new MaterialTypeRepository(db);
        } else {
            return instance;
        }
    }
    private MaterialTypeRepository(HyggeDb db) {
        this.db = db;
        instance = this;
    }

    public MaterialType getById(int id) {
        Selection selection = new Selection("material_type");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapType(rs);
    }

    private MaterialType mapType(ResultSet rs) {
        MaterialType type = null;
        try {
            if (rs.next()) {
                type = new MaterialType();
                type.setId(rs.getInt("id"));
                type.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    public List<MaterialType> findAll() {
        Selection selection = new Selection("material_type");
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapTypes(rs);
    }
    private List<MaterialType> mapTypes(ResultSet rs) {
        List<MaterialType> types = new ArrayList<>();
        MaterialType type = mapType(rs);
        while (type != null) {
            types.add(type);
            type = mapType(rs);
        }
        return types;
    }

    public List<MaterialType> findBy(Condition condition) {
        return null;
    }

    public void persist(MaterialType entity) {

    }

    public void persistAndFlush(MaterialType entity) {

    }

    public void flush() {

    }
}
