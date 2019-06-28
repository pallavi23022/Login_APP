package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  {

    EditText ed1,ed2;
    Button login;
    FirebaseAuth mauth;
    boolean isLogin=false;
    Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mauth=FirebaseAuth.getInstance();

        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        login=findViewById(R.id.login);
        sign=findViewById(R.id.sign);



  sign.setOnClickListener(new View.OnClickListener() {
               @Override
        public void onClick(View view) {
/*******************************************************************************************************************************/
//            if( ( ed1.getText().length()!=0 )   & (ed2.getText().length()!=0) )
//            {
//
//               if(String.valueOf(ed1.getText()).equals("admin") )
//               {
//                   if(String.valueOf(ed2.getText()).equals("admin"))
//                   {
//                       Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
//
//                       Intent i=new Intent(MainActivity.this,signin.class);
//                               startActivity(i);
//
//                   }
//                   else{
//                       Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
//                   }
//               }
//               else
//               {
//                   Toast.makeText(MainActivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
//               }
//
//            }
//            else
//            {
//                Toast.makeText(MainActivity.this, "please enter your userid and password", Toast.LENGTH_SHORT).show();
//            }
/***********************************************************************************************************************************************************/
                    // email code *************************************************************************************************
                   // sign in part************

                   if(ed1.getText().length()!=0  & ed2.getText().length()!=0 )
                   {

                       mauth.createUserWithEmailAndPassword(""+ed1.getText(),""+ed2.getText())// jo bhi vha enter kiya vo save ho jayega firebase mai authentication mai
                               .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                       {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                   Toast.makeText(MainActivity.this, "succesfully registered", Toast.LENGTH_SHORT).show();

                               }
                               else{
                                   Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                               }

                           }
                       });// yha hmne new user create kiya hai for sign in


                   }
       }
    });

        /***************************************************/ // login vala code

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                          if( (ed1.getText().length()!=0) & ed2.getText().length()!=0 )
                          {
                              mauth.signInWithEmailAndPassword(ed1.getText().toString() ,ed2.getText().toString() ).
                                           addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   if(task.isSuccessful())
                                                   {
                                                       Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                                       startActivity(new Intent(getApplicationContext(),signin.class));
                                                   }
                                                   else
                                                   {
                                                       Toast.makeText(MainActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                                                   }

                                               }
                                           });
                          }

            }
        });

    }

/*****************************************************************************************/ // Start krte hi ye code run hoga
// check krega ki already agr user login hai to next intent / activity open ho jaye nhi to isLogin true kr de
    @Override
    protected void onStart() // check krenge ki user already login to nhi hai
    {
        super.onStart();

        FirebaseUser user=mauth.getCurrentUser();// user login hai ya nhi check kring
        if(user!=null){ // agr user null hai mtln login nhi hai cache mwmory mai koi info nhi hai uski
              isLogin=true;
              finish();
            Intent i=new Intent(MainActivity.this,signin.class);// signin vo file hai jo login k baas=d open hui thi
            startActivity(i);// hme iss page pr le aayega agr hm already login hai
         }


    }
}