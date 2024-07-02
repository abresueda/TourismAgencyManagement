package business;

import core.Helper;
import dao.PensionDao;
import entity.Hotel;
import entity.Pension;
import entity.Period;

import java.util.ArrayList;
import java.util.List;

public class PensionManager {
    private final PensionDao pensionDao = new PensionDao();

    public PensionManager() {
    }

    public ArrayList<Pension> findAll() {
        return this.pensionDao.findAll();
    }

    public List<Object[]> findAllHotelsWithPension(int columnCount) {
        return pensionDao.findAllHotelsWithPensionTypes(columnCount);
    }

    public boolean save(Pension pension) {
        return this.pensionDao.save(pension);
    }

}
