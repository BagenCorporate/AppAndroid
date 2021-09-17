package fr.legrand.application.bagen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MesComptesActivity extends AppCompatActivity {

  private TextView txtMesComptes;
  private AccesLocale accesLocale;
  private Utilisateur utilisateur = new Utilisateur(null,null,null,null,null,null,null,null);
  private ArrayList<Compte> mesComptes;
  private ArrayList<Depenses> mesDepenses;
  private Budget monBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_comptes);

        init();
        initList();
    }

    private void init(){
      utilisateur = (Utilisateur)Serializer.deSerialize("utilisateur",getBaseContext());
      txtMesComptes = (TextView)findViewById(R.id.txtMesComptes);
      mesComptes = new ArrayList<Compte>();
      mesDepenses = new ArrayList<Depenses>();
      monBudget = new Budget(null, null, null, null);

    }

    private void recupComptesSQLITE(String idutilisateur){
      mesComptes = new ArrayList<Compte>();
      accesLocale = new AccesLocale(getBaseContext());
      mesComptes = accesLocale.recupCompte(idutilisateur);
      Log.d("mesComptes :","**************** "+mesComptes.get(0).getIntitule()+" "+mesComptes.get(1).getIntitule());

    }

    private void recupDepensesSQLITE(String idcompte){

      accesLocale = new AccesLocale(getBaseContext());
      mesDepenses = accesLocale.recupDepense(idcompte);
      //Log.d("mesDepenses :","**************** "+mesDepenses.get(0).getLibelle()+" "+mesDepenses.get(1).getLibelle());

    }

    private void recupBudgetSQLITE(String idcompte){
      monBudget = new Budget(null, null, null, null);
      accesLocale = new AccesLocale(getBaseContext());
      monBudget = accesLocale.recupBudget(idcompte);
      Log.d("monBudget :","**************** "+monBudget.getMontant());

    }

    private void recupAllSQLITE(){
      recupComptesSQLITE(utilisateur.getId());

      for(Compte compte : mesComptes){
        recupDepensesSQLITE(compte.getId());
        recupBudgetSQLITE(compte.getId());
        compte.setLeBudget(monBudget);
        compte.setLesDepenses(mesDepenses);
      }

      /*
      for(int k = 0; k < mesComptes.size(); k++){
        recupBudgetSQLITE(mesComptes.get(k).getId());
        recupDepensesSQLITE(mesComptes.get(k).getId());
        mesComptes.get(k).setLeBudget(monBudget);
        mesComptes.get(k).setLesDepenses(mesDepenses);
      }*/
    }

    private void initList(){
      recupAllSQLITE();
      ListView listView = (ListView) findViewById(R.id.lstComptes);
      CompteAdapter adapter = new CompteAdapter(MesComptesActivity.this, mesComptes,utilisateur.getId()) ;
      listView.setAdapter(adapter) ;
    }
}
