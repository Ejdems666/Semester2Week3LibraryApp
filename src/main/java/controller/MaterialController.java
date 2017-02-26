package controller;

import hyggedb.HyggeDb;
import hyggemvc.controller.Controller;
import model.Connector;
import model.entity.Material;
import model.repository.MaterialRepository;
import model.repository.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Created by adam on 26/02/2017.
 */
public class MaterialController extends Controller {
    private HyggeDb db;
    private Repository<Material> materialRepository;

    public MaterialController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        db = new HyggeDb(new Connector());
        materialRepository = MaterialRepository.getInstance(db);
    }

    public void index(){
        Integer bookId = parseMaterialId();
        if (bookId == null) {
            showAll();
        } else {
            showDetail(bookId);
        }
    }
    private Integer parseMaterialId(){
        Integer bookId = null;
        String rawBookId = request.getParameter("id");
        if (rawBookId != null) {
            try {
                bookId = Integer.parseInt(rawBookId);
            } catch (NumberFormatException e) {}
        }
        return bookId;
    }
    private void showDetail(Integer bookId) {
        Material material = materialRepository.getById(bookId);
        if (material == null) {
            renderTemplate("404");
        } else {
            request.setAttribute("material",material);
            renderTemplate("material/detail");
        }
    }

    private void showAll() {
        Collection<Material> materials = materialRepository.findAll();
        request.setAttribute("materials",materials);
        renderTemplate("material/all");
    }


}
