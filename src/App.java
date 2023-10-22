public class App implements Runnable{

    GUI gui = new GUI();
    public static void main(String[] args) throws Exception {
        new Thread(new App()).start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true){
            gui.repaint();
        }
        
    }
}
