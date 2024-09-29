package com.example.lesson_8;
import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lesson_8.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        viewBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.main);
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    viewBinding.btn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //После нажатия на кнопки появляется текст с прогрессом и сам прогресс круглый
            viewBinding.text.setVisibility(View.VISIBLE);
            viewBinding.btn.setVisibility(View.GONE);
            viewBinding.progressCircular.setVisibility(View.VISIBLE);
            //--------------------------------------------------------------

            //Создается новый поток для Для повтора цикла
            Thread thread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    //--------------------------------------------------------------


                    //Цикл Для Обновление прогресса
                        for (int i = 0; i <=100; i++) {
                            //Создается перемена в которой будет отображаться прогресс
                            // Переменная может создаваться в любом потоке Использоваться в любом потоке
                            Integer Prog =i;
                            //--------------------------------------------------------------


                            //Переход в основной поток через дверь в одну сторону Чтобы применить UI Изменения
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Изменения
                                    viewBinding.text.setText(Prog + "%");
                                    viewBinding.progressCircular.setProgress(Prog, true);

                                }
                                //--------------------------------------------------------------

                            });


                            //Каждый раз будет останавливать После завершения круга изменений
                            // Это является имитацией загрузки
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                    }
                        //Обратно переходим ui Поток чтобы применить изменения
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewBinding.lotti.setVisibility(View.VISIBLE);
                                viewBinding.lotti.playAnimation();
                                viewBinding.lotti.addAnimatorListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(@NonNull Animator animator) {
                                    }

                                    @Override
                                    public void onAnimationEnd(@NonNull Animator animator) {
                                    viewBinding.lotti.setVisibility(View.GONE);
                                    viewBinding.btnNex.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationCancel(@NonNull Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(@NonNull Animator animator) {

                                    }
                                });
                                viewBinding.text.setVisibility(View.GONE);
                                viewBinding.progressCircular.setVisibility(View.GONE);


                            }
                        });
                }
            };
            thread.start();

        }

    });

    viewBinding.btnNex.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            startActivity(new Intent(MainActivity.this,ScrollingActivity.class));
        }
    });
    }
}