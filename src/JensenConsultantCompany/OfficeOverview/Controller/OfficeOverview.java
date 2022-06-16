package JensenConsultantCompany.OfficeOverview.Controller;

import JensenConsultantCompany.Foundation.Program;

public class OfficeOverview extends Program {

    //Experimenting with threading, this is just an experiment

    public static void main(String[] args) {


        MyRunnable consul1 = new MyRunnable();
        Thread thread1 = new Thread(consul1);

        thread1.start();
    }


}
