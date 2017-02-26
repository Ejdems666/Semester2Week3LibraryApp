package model.repository;

import hyggedb.HyggeDb;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import model.entity.Material;
import model.entity.MaterialType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by adam on 26/02/2017.
 */
public class MaterialRepository implements Repository<Material> {
    private final HyggeDb db;
    private static Repository<Material> instance;

    public static Repository<Material> getInstance(HyggeDb db) {
        if (instance == null) {
            return instance = new MaterialRepository(db);
        } else {
            return instance;
        }
    }
    private MaterialRepository(HyggeDb db) {
        this.db = db;
        instance = this;

    }

    public Material getById(int id) {
        Selection selection = new Selection("material");
        selection.where("id=?",id);
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapMaterial(rs);
    }

    private Material mapMaterial(ResultSet rs) {
        Material material = null;
        try {
            if (rs.next()) {
                Repository<MaterialType> materialTypeRepository = MaterialTypeRepository.getInstance(db);
                material = new Material();
                material.setId(rs.getInt("id"));
                material.setTitle(rs.getString("title"));
                material.setLanguage(rs.getString("language"));
                material.setPublishedAt(rs.getDate("published_at"));
                material.setPublisher(rs.getString("publisher"));
                material.setMaterialType(materialTypeRepository.getById(rs.getInt("material_type_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return material;
    }

    public Collection<Material> findAll() {
        Selection selection = new Selection("material");
        ResultSet rs = db.getSelectionExecutor().getResult(selection);
        return mapMaterials(rs);
    }

    private Collection<Material> mapMaterials(ResultSet rs) {
        Collection<Material> materials = new ArrayList<Material>();
        Material material = mapMaterial(rs);
        while (material != null) {
            materials.add(material);
            material = mapMaterial(rs);
        }
        return materials;
    }

    public Collection<Material> findBy(Condition condition) {
        return null;
    }

    public void persist(Material entity) {

    }

    public void persistAndFlush(Material entity) {

    }

    public void flush() {

    }
}
