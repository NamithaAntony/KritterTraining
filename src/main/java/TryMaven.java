import org.slf4j.*;
public class TryMaven {


        void print(){
            Logger log = LoggerFactory.getLogger("Test");
            //Logging the information
            log.trace("info using slf4j");
        }

        public static void main(String[] args) {
            TryMaven t=new TryMaven();
            t.print();
            System.out.println("Exit");
        }

}
