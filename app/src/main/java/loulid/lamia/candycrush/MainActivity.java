package loulid.lamia.candycrush;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int [] candies = {
            R.drawable.bluecandy,
            R.drawable.greencandy,
            R.drawable.redcandy,
            R.drawable.orangecandy,
            R.drawable.purplecandy,
            R.drawable.yellowcandy,
    };
    //creation de 3 variables pour définir le nmbr de block qui sera adapté a tt les tailles d'ecran et grille et la largeur de l'ecran
    int widthOfBlock,noOfBlock = 8 ,widthOfScreen;
    //stocker tout les images de candies
    ArrayList<ImageView> candy= new ArrayList<>();
    int candyToBeDragged, candyToBeReplaced; // creation deux entiers un pour le candy remplace et un pour l'endommagé
    int notCandy = R.drawable.ic_launcher_background;
    Handler mHandler;
    int interval = 100;//ms
    TextView scoreResult;
    int score=0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreResult = findViewById(R.id.score);
        /*classe de matrice pour vérifier la largeur et la hauteur *DisplayMetrics matrice d'affichage pour obtenir le gestionnaire de fenetre*/
        DisplayMetrics displayMetrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthOfScreen = displayMetrics.widthPixels;//largeur de l'écran
        int heightOfScreen = displayMetrics.heightPixels;
        widthOfBlock = widthOfScreen / noOfBlock;//largeur du block = largeur d'ecran sur le nb de block pour définir la larg et haut des candies
        createBoard();
        for(final ImageView imageView : candy){
            imageView.setOnTouchListener(new OnSwipeListner(this)
            {
                @Override
                void OnSwipeLeft() {
                    super.OnSwipeLeft();
                    candyToBeDragged= imageView.getId();
                    candyToBeReplaced=candyToBeDragged -1;
                    candyInterChange();
                }

                @Override
                void OnSwipeRight() {
                    super.OnSwipeRight();
                    candyToBeDragged= imageView.getId();
                    candyToBeReplaced=candyToBeDragged +1;
                    candyInterChange();
                    //Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
                }

                @Override
                void OnSwipeTop() {
                    super.OnSwipeTop();
                    candyToBeDragged= imageView.getId();
                    candyToBeReplaced=candyToBeDragged - noOfBlock;
                    candyInterChange();
                }

                @Override
                void OnSwipeBottom() {
                    super.OnSwipeBottom();
                    candyToBeDragged= imageView.getId();
                    candyToBeReplaced=candyToBeDragged + noOfBlock;
                    candyInterChange();
                }
            });
        }
        mHandler = new Handler();
        startRepeat();
    }

    private void checkRowForThree()
    {
        for (int i=0; i < 62 ; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int)candy.get(x++).getTag() == chosedCandy &&
                        (int)candy.get(x).getTag() == chosedCandy)
                {
                    score =score+3;
                    scoreResult.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);

                }
            }

        }
        moveDownCandies();
    }
    private void checkColumnForThree()
    {
        for (int i=0; i < 47 ; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
                int x = i;
                if((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int)candy.get(x+noOfBlock).getTag() == chosedCandy &&
                        (int)candy.get(x+2*noOfBlock).getTag() == chosedCandy)
                {
                    score =score+3;
                    scoreResult.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x=x+noOfBlock;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x=x+noOfBlock;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                }

            }

        moveDownCandies();
    }

    private void moveDownCandies()
    {
        Integer[] firstRow = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer> list=Arrays.asList(firstRow);
        for(int i = 55; i >=0 ;i--)
        {
            if((int)candy.get(i + noOfBlock).getTag() == notCandy)
            {
                candy.get(i+noOfBlock).setImageResource((int) candy.get(i).getTag());
                candy.get(i+noOfBlock).setTag(candy.get(i).getTag());
                candy.get(i).setImageResource(notCandy);
                candy.get(i).setTag(notCandy);

                if (list.contains(i) && (int) candy.get(i).getTag() == notCandy)
                {
                    int randomColor = (int) Math.floor(Math.random() * candies.length);
                    candy.get(i).setImageResource(candies[randomColor]);
                    candy.get(i).setTag(candies[randomColor]);
                }
            }
        }
        for(int i = 0; i <8 ; i++)
        {
            if ((int) candy.get(i).getTag() == notCandy)
            {
                int randomColor = (int) Math.floor(Math.random() * candies.length);
                candy.get(i).setImageResource(candies[randomColor]);
                candy.get(i).setTag(candies[randomColor]);
            }
        }
    }

    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try{
                checkRowForThree();
                checkColumnForThree();
                moveDownCandies();
                //Toast.makeText(MainActivity.this, "100", Toast.LENGTH_SHORT).show();
                // gestion d'exception qq soit le code de retour
            }finally {
                mHandler.postDelayed(repeatChecker,interval);//début de répitition
            }
        }
    };
    void startRepeat()
    {
        repeatChecker.run();
    }

    // les images intérchagent leur backgroun
    private void candyInterChange(){
        int background = (int) candy.get(candyToBeReplaced).getTag();
        int background1 = (int) candy.get(candyToBeDragged).getTag();
        candy.get(candyToBeDragged).setImageResource(background);
        candy.get(candyToBeReplaced).setImageResource(background1);
        candy.get(candyToBeDragged).setTag(background);
        candy.get(candyToBeReplaced).setTag(background1);
        //Log.d("DEBUG", " imgView " + candy.get(candyToBeReplaced));
        //Log.d("DEBUG", " getTag " +  candy.get(candyToBeReplaced).getTag());

    }

    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.board);
        gridLayout.setRowCount(noOfBlock);
        gridLayout.setColumnCount(noOfBlock);
        // mise en page carrée  de la meme hauteur et largeur
        gridLayout.getLayoutParams().width = widthOfScreen;
        gridLayout.getLayoutParams().height = widthOfScreen;
        // ajouter des bonbons comme vue d'image dans chaque grille
        for(int i = 0;i<noOfBlock *noOfBlock;i++){
            ImageView imageView = new  ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new
                    android.view.ViewGroup.LayoutParams(widthOfBlock,widthOfBlock));//puisque on veut un carré
            imageView.setMaxHeight(widthOfBlock);
            imageView.setMaxWidth(widthOfBlock);
            int randomCandy = (int) Math.floor(Math.random()* candies.length);//génerer les index de candies d'une maniére aléatoire
            //index aléatoire pour le tableau de candies
            imageView.setImageResource(candies[randomCandy]);
            imageView.setTag(candies[randomCandy]);
            candy.add(imageView);
            gridLayout.addView(imageView);


        }
    }
}