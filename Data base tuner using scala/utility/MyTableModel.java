package utility;
import javax.swing.table.*;
import java.util.Vector;

class MyTableModel extends DefaultTableModel {
    
    Vector cols;
    Vector<Vector> data;
    
    public MyTableModel() {
        initCol();
        initData();
        super.setDataVector(data, cols);// we also write this keyword
        System.out.println(this.getColumnName(2));
        System.out.println(this.getColumnCount());
        System.out.println("hi");
        
    }
    
    void initCol() {
        cols = new Vector();
        cols.add("SELECT");
        cols.add("ID");
        cols.add("NAME");
        cols.add("SALARY");
    }
    
    void initData() {
        data = new Vector(); //data vector
        Vector e1 = new Vector();
        e1.add(false);
        e1.add(101);
        e1.add("Raj");
        e1.add(25000);
        data.add(e1);
        
        Vector e2 = new Vector();
        e2.add(false);
        e2.add(102);
        e2.add("Amit");
        e2.add(26000);
        data.add(e2);
        
        Vector e3 = new Vector();
        e3.add(false);
        e3.add(103);
        e3.add("Vivek");
        e3.add(89000);
        data.add(e3);
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            //first column
            Class type = Boolean.class;
            return type;
        } else {
            return super.getColumnClass(columnIndex); //unchanged
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 1) {
            //ID col is non-editable
            return false;
        } else {
            return true;
        }

        /*
        if(row==2 && column==3){
            return false;                
        }else{
            return true;
        }
         */
    }
}

//////////////////end inner class//////  


