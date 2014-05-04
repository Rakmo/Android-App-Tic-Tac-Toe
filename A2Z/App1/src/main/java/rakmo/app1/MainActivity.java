package rakmo.app1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    boolean flagWin = false;
    boolean flagSquaresFilled = false;
    boolean flagTurn = true;

    private static int size = 3;
    public static String mainMatrix[][] = new String[size][size];
    private static boolean visitedIndex[][] = new boolean[size][size];
    public static Map<Integer, String> coordinates = new HashMap<Integer, String>();
    public static Map<Integer, String> positionsToCheck = new HashMap<Integer, String>();
    private static Dialog dlog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //-------------------------------------------
        positionsToCheck.put(1, "1,2,3::1,5,9::1,4,7");
        positionsToCheck.put(2, "1,2,3::2,5,8");
        positionsToCheck.put(3, "1,2,3::3,5,7::3,6,9");
        positionsToCheck.put(4, "4,5,6::1,4,7");
        positionsToCheck.put(5, "2,5,8::1,5,9::4,5,6::3,5,7");
        positionsToCheck.put(6, "3,6,9::4,5,6");
        positionsToCheck.put(7, "1,4,7::7,8,9::3,5,7");
        positionsToCheck.put(8, "2,5,8::7,8,9");
        positionsToCheck.put(9, "1,5,9::3,6,9,::7,8,9");
        //-------------------------------------------
        int k = 1;
        for(int i = 0; i<size; i++)
        {
            for(int j = 0; j<size; j++)
            {
                coordinates.put(k, ""+i+","+j);
                k++;
            }
        }
        //-------------------------------------------
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

    }

    public void playTicTacToe(View view)
    {
        int id = view.getId();
        ImageButton iButton = (ImageButton) findViewById(id);
        int position = Integer.parseInt(getResources().getResourceEntryName(id).substring(6).trim());
        TextView txtTurn = (TextView) findViewById(R.id.txtTurn);
        if(iButton.isEnabled())
        {
            if(flagTurn)
            {
                iButton.setImageResource(R.drawable.x_square);
                iButton.setEnabled(false);
                fillThePosition(flagTurn, position);
                flagWin = isGameFinished(flagTurn, position);
                flagSquaresFilled = areSqauresFilled(flagTurn, position);

                if(flagWin)
                {
                    //System.out.println("***** The Winner of the Game: X *****");
                    showAlert(flagTurn, "Winner! Congrats!!", "Click on new game to continue...", this);
                }

                if(!flagWin) {
                    if (flagSquaresFilled) {
                        showAlert("Tie Game !!!", "Click on new game to continue...", this);
                    }
                }

                if(!flagWin && !flagSquaresFilled)
                {
                    txtTurn.setText(R.string.o_turn);
                    flagTurn = false;
                }
            }
            else
            {
                iButton.setImageResource(R.drawable.o_square);
                iButton.setEnabled(false);
                fillThePosition(flagTurn, position);
                flagWin = isGameFinished(flagTurn, position);
                flagSquaresFilled = areSqauresFilled(flagTurn, position);

                if(flagWin)
                {
                    //disableRemainingSquares();
                    showAlert(flagTurn, "Winner! Congrats!!", "Click on new game to continue...", this);

                }

                if(flagSquaresFilled)
                {
                    showAlert("Tie Game !!!", "Click on new game to continue...", this);
                }

                if(!flagWin && !flagSquaresFilled)
                {
                    txtTurn.setText(R.string.x_turn);
                    flagTurn = true;
                }
            }
        }
    }


    public void playNewGame(View view)
    {
        mainMatrix = new String[size][size];
        visitedIndex = new boolean[size][size];
        flagWin = false;
        flagSquaresFilled = false;
        flagTurn = true;

        ImageButton tempImgBtn = (ImageButton) findViewById(R.id.button1);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        tempImgBtn = (ImageButton) findViewById(R.id.button2);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        tempImgBtn = (ImageButton) findViewById(R.id.button3);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        tempImgBtn = (ImageButton) findViewById(R.id.button4);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        tempImgBtn = (ImageButton) findViewById(R.id.button5);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        tempImgBtn = (ImageButton) findViewById(R.id.button6);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        tempImgBtn = (ImageButton) findViewById(R.id.button7);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        tempImgBtn = (ImageButton) findViewById(R.id.button8);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        tempImgBtn = (ImageButton) findViewById(R.id.button9);
        tempImgBtn.setImageResource(R.drawable.blank_square);
        tempImgBtn.setEnabled(true);

        TextView tempTextView = (TextView) findViewById(R.id.txtTurn);
        tempTextView.setText(R.string.x_turn);
    }

    public void doExit(View view)
    {
        this.finish();
       /* Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_GALLERY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
    }

    private static void fillThePosition(boolean turn, int position)
    {
        String[] ordinates = coordinates.get(position).split(",");
        int x = Integer.parseInt(ordinates[0]);
        int y = Integer.parseInt(ordinates[1]);

        if(turn)
        {
            mainMatrix[x][y] = "X";
            visitedIndex[x][y] = true;
        }
        else
        {
            mainMatrix[x][y] = "O";
            visitedIndex[x][y] = true;
        }
    }

    private static boolean areSqauresFilled(boolean flagTurn, int position)
    {
        int count = 0;
        for(int i=0; i<size; i++)
        {
            for(int j = 0; j<size; j++)
            {
                if(visitedIndex[i][j])
                    count++;
            }
        }
        if(count == 9)
            return true;

        return false;
    }

    private static boolean isGameFinished(boolean flagTurn, int position)
    {
        boolean flagFinished = false;
        String checks[] = positionsToCheck.get(position).split("::");
        for(int i=0; i<checks.length; i++)
        {
            String positions[] = checks[i].split(",");
            flagFinished = checkPositions(flagTurn, positions) ;
            if(flagFinished)
                return true;
        }
        return false;
    }

    private static boolean checkPositions(boolean flagTurn, String[] positions)
    {
        int countX = 0;
        int countO = 0;
        for(int i=0; i<positions.length; i++)
        {
            String ordinates[] = coordinates.get(Integer.parseInt(positions[i])).split(",");
            int x = Integer.parseInt(ordinates[0]);
            int y = Integer.parseInt(ordinates[1]);
            if(visitedIndex[x][y])
            {
                if(mainMatrix[x][y].equals("X"))
                    countX++;
                else if(mainMatrix[x][y].equals("O"))
                    countO++;
            }
            else
            {
                return false;
            }
        }

        if(flagTurn && countX == 3)
            return true;
        else if(!flagTurn && countO == 3)
            return true;
        else
            return false;
    }


    private void showAlert(String title, String message, Context context)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);


        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting tie Icon to Dialog
        alertDialog.setIcon(R.drawable.tie_game_square);

        // Setting New Game Button
        alertDialog.setNeutralButton(R.string.new_game, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                MainActivity.this.playNewGame(findViewById(R.id.containerT));
            }
        });
        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    private void showAlert(boolean turn, String title, String message, Context context)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Winner's Icon to Dialog
        if(turn)
            alertDialog.setIcon(R.drawable.x_square);
        else
            alertDialog.setIcon(R.drawable.o_square);

        // Setting New Game Button
        alertDialog.setNeutralButton(R.string.new_game, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                MainActivity.this.playNewGame(findViewById(R.id.containerT));
            }
        });

        alertDialog.setCancelable(false);
        // Showing Alert Message
        alertDialog.show();

    }

}
