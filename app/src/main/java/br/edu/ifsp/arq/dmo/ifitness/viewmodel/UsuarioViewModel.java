package br.edu.ifsp.arq.dmo.ifitness.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Optional;

import br.edu.ifsp.arq.dmo.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmo.ifitness.model.UsuarioComAtividade;
import br.edu.ifsp.arq.dmo.ifitness.repository.UsuariosRepository;

public class UsuarioViewModel extends AndroidViewModel {

    public static final String USUARIO_ID = "USUARIO_ID";

    private UsuariosRepository usuariosRepository;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        this.usuariosRepository = new UsuariosRepository(application);
    }

    public LiveData<Usuario> login(String email, String senha){
        return usuariosRepository.login(email, senha);
    }

    public void logout(){
        PreferenceManager.getDefaultSharedPreferences(getApplication()).edit()
                .remove(USUARIO_ID).apply();
    }

    public LiveData<UsuarioComAtividade> isLogged(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());

        Optional<String> id = Optional.ofNullable(sharedPreferences.getString(USUARIO_ID, null));
        if(!id.isPresent()){
            return new MutableLiveData<>(null);
        }

        return usuariosRepository.load(id.get());
    }

    public void createUsuario(Usuario usuario){
        usuariosRepository.createUsuario(usuario);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Boolean update(UsuarioComAtividade usuarioComAtividade){
        return usuariosRepository.update(usuarioComAtividade);
    }

    public LiveData<List<Usuario>> allUsuarios() {
        return usuariosRepository.allUsuarios();
    }
}
