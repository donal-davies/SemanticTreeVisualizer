package CapstonePackage;

public class Tuple<I, J, K> {
        private I first;
        private J second;
        private K third;
        
        public Tuple(I f, J s, K t){
            first = f;
            second = s;
            third = t;
        }
        
        public Tuple(Object[] params){
            try{
                first = (I)params[0];
                second = (J)params[1];
                third = (K)params[2];
            }catch(Exception e){
                System.out.println("Invalid tuple size given");
                first = null;
                second = null;
                third = null;
            }
        }
        
        public I first(){
            return first;
        }
        
        public J second(){
            return second;
        }
        
        public K third(){
            return third;
        }
    }