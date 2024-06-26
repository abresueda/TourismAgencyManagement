package business;

import dao.PeriodDao;
import entity.Period;

import java.util.ArrayList;

public class PeriodManager {
    private final PeriodDao periodDao = new PeriodDao();

    public ArrayList<Period> findAll() {
        return this.periodDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Period> periodList) {
        ArrayList<Object[]> periodObjList = new ArrayList<>();
        for (Period obj: periodList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getPeriodId();
            rowObject[i++] = obj.getHotelId();
            rowObject[i++] = obj.getStartDate();
            rowObject[i++] = obj.getEndDate();
            periodObjList.add(rowObject);
        }
        return periodObjList;
    }
}
