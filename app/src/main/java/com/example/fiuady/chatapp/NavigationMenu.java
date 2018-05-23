package com.example.fiuady.chatapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NavigationMenu extends AppCompatActivity {

    private ChatDatabase db;
    private RecyclerView recyclerContainer;
    private UsersAdapter usersAdapter;
    private ChatsAdapter chatsAdapter;
    private GroupsAdapter groupsAdapter;
    private TextView tvuserperfil;
    private String avatar;
    private ImageView avatarToolBar;
    private TextView actualusername;

    private int my_id = ActualUser.id;
    private int total_users;
    private int total_groups;

    private List<Chat> rv_chats_data = new ArrayList<>();
    private List<Group> rv_groups_data = new ArrayList<>();
    private List<User> rv_users_data = new ArrayList<>();

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        public ImageView imageView;

        public ImageView getImageView() {
            return imageView;
        }

        // Keep all Images in array
        public Integer[] mThumbIds = {
                R.drawable.avatar_1, R.drawable.avatar_2,
                R.drawable.avatar_3, R.drawable.avatar_4,
                R.drawable.avatar_5, R.drawable.avatar_6

        };

        // Constructor
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public Context getMContext() {
            return mContext;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
            /*
            Crear un nuevo Image View de 90x90
            y con recorte alrededor del centro
             */
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }

            //Setear la imagen desde el recurso drawable
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
//            imageView = new ImageView(mContext);
//            imageView.setImageResource(R.drawable.avatar_2);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
//            return imageView;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_chats:
                    fillChatsAdapter();
                    return true;

                case R.id.navigation_groups:
                    fillGroupsAdapter();
                    return true;

                case R.id.navigation_contacts:
                    fillContactsAdapter();
                    return true;
            }
            return false;
        }
    };

    void fillChatsAdapter() {
        rv_chats_data.clear();
        total_users = db.chatDao().getMaxIdUsers() + 1;
        for (int i = 0; i < total_users; i++) {
            if (db.chatDao().checkStartedChatWithContact(i, my_id) > 0 && my_id != i) {
                rv_chats_data.add(new Chat(
                        i,
                        db.chatDao().getUserNameById(i),
                        db.chatDao().getUserNameById(i),
                        db.chatDao().getLastMessageOfContact(i, my_id),
                        db.chatDao().getLastDateOfContact(i, my_id)));
            }
        }
        //Sorting by lastDate of lastMessage received or sent
        Collections.sort(rv_chats_data, new Comparator<Chat>() {
            @Override
            public int compare(Chat o1, Chat o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        chatsAdapter = new ChatsAdapter(rv_chats_data);
        recyclerContainer.setAdapter(chatsAdapter);
    }

    void fillGroupsAdapter() {
        rv_groups_data.clear();
        total_groups = db.chatDao().getMaxIdGroups() + 1;
        for (int i = 0; i < total_groups; i++) {
            if (db.chatDao().checkStartedGroup(i, my_id) > 0) {
                rv_groups_data.add(new Group(
                        i,
                        db.chatDao().getGroupNameById(i),
                        db.chatDao().getLastMessageByGroupId(i),
                        db.chatDao().getLastDateByGroupId(i)));
            }
        }
        //Sorting by lastDate of lastMessage received or sent
        Collections.sort(rv_groups_data, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        groupsAdapter = new GroupsAdapter(rv_groups_data);
        recyclerContainer.setAdapter(groupsAdapter);
    }

    void fillContactsAdapter() {
        //I'm using Users adapter and class to fill the information since I've not created the Contacts table
        total_users = db.chatDao().getMaxIdUsers() + 1;
        rv_users_data.clear();
        for (int i = 0; i < total_users; i++) {
            if (i != my_id) {
                rv_users_data.add(new User(
                        i,
                        db.chatDao().getUserNameById(i),
                        db.chatDao().getPasswordById(i)));
            }
        }
        usersAdapter = new UsersAdapter((rv_users_data));
        recyclerContainer.setAdapter(usersAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);

        Stetho.initializeWithDefaults(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        db = ChatDatabase.getDatabase(NavigationMenu.this);

        recyclerContainer = findViewById(R.id.recycler_view_container);
        recyclerContainer.setLayoutManager(new LinearLayoutManager(this));
        tvuserperfil = findViewById(R.id.user_perfil);
        avatarToolBar = findViewById(R.id.app_bar_image);
        tvuserperfil.setText(db.chatDao().getUserNameById(0));
        avatar = db.chatDao().getAvatarUser(0);

        switch (avatar) {
            case "avatar_0":
                avatarToolBar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_1));
                break;
            case "avatar_1":
                avatarToolBar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_2));
                break;
            case "avatar_2":
                avatarToolBar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_3));
                break;
            case "avatar_3":
                avatarToolBar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_4));
                break;
            case "avatar_4":
                avatarToolBar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_5));
                break;
            case "avatar_5":
                avatarToolBar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_6));
                break;
        }
        fillChatsAdapter(); //Starts activity with chatsAdapter


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.usuario_menu:
                //Toast.makeText(this, "cambiar usuario", Toast.LENGTH_SHORT).show();
                //actualusername=findViewById(R.id.actualusername_tv);
                //actualusername.setText(db.chatDao().getUserNameById(0));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.cambiar_usuario, null);
                builder.setView(dialogView);
                actualusername=dialogView.findViewById(R.id.actualusername_tv);
                actualusername.setText(db.chatDao().getUserNameById(0));
                builder.setTitle("CAMBIAR USUARIO ACTUAL")
                        .setMessage("Por favor Llene la información requerida.")
                        .setIcon(avatarToolBar.getDrawable());
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(NavigationMenu.this, "Usuario Actualizado Corectamente", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NavigationMenu.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.contraseña_menu:
               // Toast.makeText(this, "cambiar contra", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                LayoutInflater inflater1 = this.getLayoutInflater();
                View dialogView1 = inflater1.inflate(R.layout.cambiar_contrasena, null);
                builder1.setView(dialogView1);
                builder1.setTitle("CAMBIAR CONTRASEÑA ACTUAL")
                        .setMessage("Por favor Llene la información requerida.")
                        .setIcon(avatarToolBar.getDrawable());
                builder1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(NavigationMenu.this, "Usuario Actualizado Corectamente", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NavigationMenu.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setCancelable(false);
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                return true;
            case R.id.estado_menu:
                //Toast.makeText(this, "cambiar estado", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                LayoutInflater inflater2 = this.getLayoutInflater();
                View dialogView2 = inflater2.inflate(R.layout.cambiar_estado, null);
                builder2.setView(dialogView2);
                builder2.setTitle("CAMBIAR ESTADO ACTUAL")
                        .setMessage("Por favor Llene la información requerida.")
                        .setIcon(avatarToolBar.getDrawable());
                builder2.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(NavigationMenu.this, "Usuario Actualizado Corectamente", Toast.LENGTH_SHORT).show();
                    }
                });
                builder2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NavigationMenu.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder2.setCancelable(false);
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                return true;
            case R.id.avatar_menu:
               // Toast.makeText(this, "cambiar avatar", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                LayoutInflater inflater3 = this.getLayoutInflater();
                View dialogView3 = inflater3.inflate(R.layout.cambiar_avatar, null);
                builder3.setView(dialogView3);
                final GridView gridView = (GridView) dialogView3.findViewById(R.id.gridview);
                gridView.setAdapter(new NavigationMenu.ImageAdapter(this));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0:
                                view.setBackgroundColor(Color.BLUE);
                                avatar = "avatar_0";
                                break;
                            case 1:
                                view.setBackgroundColor(Color.BLUE);
                                avatar = "avatar_1";
                                break;
                            case 2:
                                view.setBackgroundColor(Color.BLUE);
                                avatar = "avatar_2";
                                break;
                            case 3:
                                view.setBackgroundColor(Color.BLUE);
                                avatar = "avatar_3";
                                break;
                            case 4:
                                view.setBackgroundColor(Color.BLUE);
                                avatar = "avatar_4";
                                break;
                            case 5:
                                view.setBackgroundColor(Color.BLUE);
                                avatar = "avatar_5";
                                break;
                        }

                    }
                });
                builder3.setTitle("CAMBIAR AVATAR ACTUAL")
                        .setMessage("Por favor Llene la información requerida.")
                        .setIcon(avatarToolBar.getDrawable());
                builder3.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(NavigationMenu.this, "Usuario Actualizado Corectamente", Toast.LENGTH_SHORT).show();
                    }
                });
                builder3.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NavigationMenu.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder3.setCancelable(false);
                AlertDialog dialog3 = builder3.create();
                dialog3.show();
                return true;
            case R.id.cerrar_sesion_menu:
                UsersTable user = new UsersTable(0, "vacío", "*****", "Disponible", "avatar_0");
                db.chatDao().UpdateUser(user);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0X01:
                if (resultCode == RESULT_OK) {
                    fillChatsAdapter();
                }
                break;
            case 0x02:
                if (resultCode == RESULT_OK) {
                    //fillGroupsAdapter();
                }
                break;
            default:
                //other activities
                break;
        }
    }


}
