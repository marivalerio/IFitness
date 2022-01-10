package br.edu.ifsp.arq.dmo.ifitness.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifsp.arq.dmo.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmo.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmo.ifitness.model.UsuarioComAtividade;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class UsuariosRepository {

    private static final String BASE_URL = "https://identitytoolkit.googleapis.com/v1/";
    private static final String SIGNUP = "accounts:signUp";
    private static final String SIGNIN = "accounts:signInWithPassword";
    private static final String KEY = "?key=AIzaSyAIEpJ5gvLNfW3RwFOU6NCyhSi011WQ6Lk";

    private FirebaseFirestore firestore;

    private RequestQueue queue;

    private SharedPreferences preference;


    public UsuariosRepository(Application application) {
        firestore = FirebaseFirestore.getInstance();
        queue = Volley.newRequestQueue(application);
        preference = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public LiveData<Usuario> login(String email, String senha) {
        MutableLiveData<Usuario> liveData = new MutableLiveData<>();
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("email", email);
            parametros.put("password", senha);
            parametros.put("returnSecureToken", true);
            parametros.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + SIGNIN + KEY, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String localId = response.getString("localId");
                            String idToken = response.getString("idToken");
                            firestore.collection("usuario").document(localId).get().addOnSuccessListener(snapshot -> {
                                Usuario usuario = snapshot.toObject(Usuario.class);
                                usuario.setId(localId);
                                usuario.setSenha(idToken);
                                liveData.setValue(usuario);
                                preference.edit().putString(UsuarioViewModel.USUARIO_ID, localId).apply();
                                firestore.collection("usuario").document(localId).set(usuario);
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                                liveData.setValue(null);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });
        queue.add(request);
        return liveData;
    }

    public void createUsuario(Usuario usuario){
        JSONObject parametros = new JSONObject();
        try{
            parametros.put("email", usuario.getEmail());
            parametros.put("password", usuario.getSenha());
            parametros.put("returnSecureToken", true);
            parametros.put("Content-Type", "application/json; charset=utf-8");
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + SIGNUP + KEY, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            usuario.setId(response.getString("localId"));
                            usuario.setSenha(response.getString("idToken"));

                            firestore.collection("usuario").document(usuario.getId()).set(usuario).addOnSuccessListener( unused -> {
                                Log.d(this.toString(), "Usuário " + usuario.getEmail() + " cadastrado com sucesso!");
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                            } catch ( UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });
        queue.add(request);
    }

    public LiveData<UsuarioComAtividade> load(String usuarioId) {
        UsuarioComAtividade usuarioComAtividade = new UsuarioComAtividade();
        MutableLiveData<UsuarioComAtividade> liveData = new MutableLiveData<>();

        DocumentReference userRef = firestore.collection("usuario").document(usuarioId);

        userRef.get().addOnSuccessListener(snapshot -> {
            Usuario usuario = snapshot.toObject(Usuario.class);
            usuario.setId(usuario.getId());

            usuarioComAtividade.setUsuario(usuario);

            userRef.collection("atividade").get().addOnCompleteListener( snap -> {
                snap.getResult().forEach(doc -> {
                    Atividade atividade = doc.toObject(Atividade.class);
                    atividade.setId(atividade.getId());
                    usuarioComAtividade.getAtividades().add(atividade);
                });
                liveData.setValue(usuarioComAtividade);
            });
        });
        return liveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Boolean update(UsuarioComAtividade usuarioComAtividade) {
        final Boolean[] atualizado = {false};

        DocumentReference usuarioRef = firestore.collection("usuario")
                .document(usuarioComAtividade.getUsuario().getId());

        usuarioRef.set(usuarioComAtividade.getUsuario()).addOnSuccessListener(unused -> {
            atualizado[0] = true;
        });

        CollectionReference atividadeRef = usuarioRef.collection("atividade");

        usuarioComAtividade.getAtividades().forEach(atividade -> {
            if(atividade.getId().isEmpty()){
                atividadeRef.add(atividade).addOnSuccessListener(it -> {
                    atividade.setId(it.getId());
                    atualizado[0] = true;
                });
            }else{
                atividadeRef.document(atividade.getId()).set(atividade).addOnSuccessListener(unused -> {
                    atualizado[0] = true;
                });
            }
        });

        return atualizado[0];
    }

    public MutableLiveData<List<Usuario>> allUsuarios() {
        MutableLiveData<List<Usuario>> liveData = new MutableLiveData<>();

        firestore.collection("usuario").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Usuario> lista = new ArrayList<>();
                    Usuario usuario = null;
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        usuario = doc.toObject(Usuario.class);
                        usuario.setId(doc.getId());
                        lista.add(usuario);
                    }
                    lista = lista.stream().sorted((u1, u2) -> u2.getPontuacao().compareTo(u1.getPontuacao())).collect(Collectors.toList());
                    liveData.setValue(lista);
                }else{
                    Log.d(UsuariosRepository.this.toString(), "Erro ao obter documentos", task.getException());
                }
            }
        });

        return liveData;
    }
}
