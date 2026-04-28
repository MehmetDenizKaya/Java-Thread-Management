import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RabbitMain {
    private static volatile boolean gameRunning = true;

    public static void main(String[] args) throws InterruptedException {

        int settings [] = new int [5];
        getValues(settings);
        Object [][] gameArray = new Object [settings[0]+1][settings[1]]; //will hold rabbits + carrot

        for (int j = 0; j < settings[0]; j++) {//start game
            gameArray[j][0] = new Rabbit();
        }

        Thread carrotSpawner = new Thread(() -> {
            try {
                while (gameRunning) {
                    Random rand_carrot = new Random();
                    int randomBox = rand_carrot.nextInt(settings[1]);

                    synchronized (gameArray) {
                        if (gameArray[settings[0]][randomBox] == null) {
                            gameArray[settings[0]][randomBox] = new Carrot();
                            System.out.println("Person put carrot to box " + randomBox);
                        }
                    }
                    Thread.sleep(settings[2]); 
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread rabbitMover = new Thread(() -> {
            try {
                while (gameRunning) {
                    int k = 0;
                    while (k < settings[0]) {
                        synchronized (gameArray) {
                            int rabbitsCurrentBox = 0;
                            for (int i = 0; i < settings[1]; i++) {
                                if (gameArray[k][i] instanceof Rabbit) {
                                    rabbitsCurrentBox = i;//column
                                    break;
                                }
                            }

                            if (rabbitsCurrentBox +1 < settings[1]) { 
                                gameArray[k][rabbitsCurrentBox+1] = gameArray[k][rabbitsCurrentBox];
                                gameArray[k][rabbitsCurrentBox] = null;
                                System.out.println(((Rabbit) gameArray[k][rabbitsCurrentBox + 1]) + " jumps to box " + rabbitsCurrentBox);
                            }

                            for (int j = 0;j < settings[0];j++){
                                if (gameArray[k][rabbitsCurrentBox] instanceof Rabbit && gameArray[j][rabbitsCurrentBox] instanceof Carrot) {
                                    Rabbit rabbit = (Rabbit) gameArray[k][rabbitsCurrentBox];
                                    rabbit.incrementPoint();
                                    gameArray[j][rabbitsCurrentBox] = null;
                                    System.out.println(rabbit + " eats the carrot in box " + rabbitsCurrentBox);
                                }
                            }
                            if (rabbitsCurrentBox + 1 == settings[1] - 1) {
                                gameRunning = false;
                                break;
                            }
                            
                            
                        }

                        k++;//row
                    }
                    Thread.sleep(settings[4]); 
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread carrotRemover = new Thread(() -> {
            try {
                while (gameRunning) {
                    Random rand_carrot = new Random();
                    int randomBox = rand_carrot.nextInt(settings[1]);
                    
                    Thread.sleep(settings[3]);
                    synchronized (gameArray) {
                        if (gameArray[settings[0]][randomBox] instanceof Carrot) {
                            System.out.println("Carrot in box " + randomBox + " has been removed after timeout.");
                            gameArray[settings[0]][randomBox] = null;
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        carrotSpawner.start();
        rabbitMover.start();
        carrotRemover.start();

        try {
            carrotSpawner.join();
            rabbitMover.join();
            carrotRemover.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Game over!");
        for (int k = 0; k < settings[0]; k++) {
            for (int i = 0; i < settings[1]; i++) {
                if (gameArray[k][i] instanceof Rabbit) {
                    Rabbit rabbit = (Rabbit) gameArray[k][i];
                    if (rabbit != null) {
                        System.out.println(rabbit + " has " + rabbit.getPoints() + " points.");
                    }
                }
            }
        }

    }

    static void getValues(int [] settings){
        int rab_val = 0;
        try (Scanner userScan = new Scanner(System.in)) {
            System.out.println("Rabbit Count:");
            do {
                if (rab_val > 15){
                    System.out.println("Sistemde tanimli 15 isim var, ondan dolayi tavsan degeri 15'nin ustunde olamaz");
                }
                rab_val = userScan.nextInt();
            } while (rab_val >= 16);
            
            settings[0] = rab_val;
            
            System.out.println("Box Count:");
            settings[1] = userScan.nextInt();
            
            System.out.println("X(ms) Value:");
            settings[2] = userScan.nextInt();
            
            System.out.println("Y Value:");
            settings[3] = userScan.nextInt();
            
            System.out.println("Z Value:");
            settings[4] = userScan.nextInt();
            
        } catch (Exception e){
            System.out.println("Error occured: " + e);
        }
    }
}

class Carrot {
    public static int CarrotName = 0;
    
    public Carrot(){
        CarrotName++;
    }
}

enum Name {Mustafa, Ahmet, Buğra, Zeynep, Deniz, Sena, Buse, Ayşe, Melike, Emir, Murat, Oğuzhan, Sibel, Esra, Çetin}

class GetName {
    private static List<Name> nameValues = new ArrayList<>(Arrays.asList(Name.values()));
    private Name name;

    public GetName() {
        Random rand_num = new Random();
        this.name = nameValues.get(rand_num.nextInt(nameValues.size()));
        nameValues.remove(this.name);
    }

    public Name getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}

final class Rabbit {
    private GetName selectrandom_name;
    private int point = 0;

    public Rabbit() {
        this.selectrandom_name = new GetName();
    }

    public void incrementPoint(){
        this.point++;
    }

    public int getPoints() {
        return this.point;
    }

    @Override
    public String toString() {
        return this.selectrandom_name.toString();
    }
}
