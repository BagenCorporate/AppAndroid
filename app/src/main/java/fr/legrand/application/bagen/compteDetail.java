package fr.legrand.application.bagen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class compteDetail extends AppCompatActivity {

  private TextView txtIntituleCompte;
  private TextView txtMontantBudget;
  private TextView txtMontantDepenses;
  private TextView txtMontantTotalDepenses;
  private Button btnModifierBudget;
  private Button btnAjouterDepense;
  private String idCompte;
  private ListView lstDepenses;
  private EditText etxtMontantBudget;
  private Button btnValiderBudget;
  private LinearLayout llBudget;
  private LinearLayout llBudgetModifie;
  private compteDetail activity;
  private AjouterDepenseDialog ajouterDepenseDialog ;

  /*
  variable pour le AjouterDepensedialog
   */
  private Button btnValider;
  private Button btnAnnuler;
  private EditText titre;
  private EditText montant;


  private Compte monCompte;
  private ArrayList<Depenses> mesDepenses;
  private Budget monBudget;
  private AccesLocale accesLocale;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compte_detail);


    init();
  }

  private void init(){
    llBudget = (LinearLayout)findViewById(R.id.llBudget);
    llBudgetModifie = (LinearLayout)findViewById(R.id.llBudgetModifie);
    btnValiderBudget = (Button)findViewById(R.id.btnValiderBudget);
    etxtMontantBudget = (EditText)findViewById(R.id.etxtMontantBudget);
    txtIntituleCompte = (TextView)findViewById(R.id.txtIntituleCompte);
    txtMontantBudget = (TextView)findViewById(R.id.txtMontantBudget);
    txtMontantDepenses = (TextView)findViewById(R.id.txtMontantDepenses);
    txtMontantTotalDepenses = (TextView)findViewById(R.id.txtMontantTotalDepenses);
    btnAjouterDepense = (Button)findViewById(R.id.btnAjouterDepenses);
    btnModifierBudget = (Button)findViewById(R.id.btnModifierBudget);
    lstDepenses = (ListView)findViewById(R.id.lstDepenses);
    idCompte = Serializer.deSerialize("numCompte",getBaseContext()).toString();
    recupCompteSQLITE(idCompte);
    recupBudgetSQLITE(idCompte);
    recupDepensesSQLITE(idCompte);
    monCompte.setLesDepenses(mesDepenses);
    monCompte.setLeBudget(monBudget);
    txtIntituleCompte.setText(monCompte.getIntitule());
    txtMontantTotalDepenses.setText(String.valueOf(Float.parseFloat(monCompte.getLeBudget().getMontant())-Float.parseFloat(monCompte.getTotalDepense()))+" €");
    txtMontantDepenses.setText(monCompte.getTotalDepense()+" €");
    txtMontantBudget.setText(monCompte.getLeBudget().getMontant()+" €");

    clickModifieBudget();
    clickValiderBudget();
    clickAjouterDepense();

    DepenseAdapter adapter = new DepenseAdapter(compteDetail.this, mesDepenses, idCompte) ;
    lstDepenses.setAdapter(adapter) ;
    //etxtMontantBudget.setVisibility(View.VISIBLE);

  }

  private void recupCompteSQLITE(String idcompte){
    monCompte = new Compte(null,null,null,null,null,null);
    accesLocale = new AccesLocale(getBaseContext());
    monCompte = accesLocale.recupCompteId(idcompte);
    Log.d("mesComptes :","**************** "+monCompte.getIntitule());

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

  private void clickModifieBudget(){
    btnModifierBudget.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        invisibleLayout(llBudget);
        visibleLayout(llBudgetModifie);
        etxtMontantBudget.setText(monCompte.getLeBudget().getMontant());
        etxtMontantBudget.requestFocus();
      }
    });
  }

  private void clickValiderBudget(){
    btnValiderBudget.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        accesLocale = new AccesLocale(getBaseContext());
        accesLocale.updateBudget(etxtMontantBudget.getText().toString(), monBudget.getId());
        visibleLayout(llBudget);
        invisibleLayout(llBudgetModifie);
        init();
      }
    });
  }

  private void visibleLayout(LinearLayout linearLayout){
    linearLayout.setVisibility(View.VISIBLE);
    ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
    //Changes the height and width to the specified *pixels*
    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
    linearLayout.setLayoutParams(params);
  }

  private void invisibleLayout(LinearLayout linearLayout){
    linearLayout.setVisibility(View.INVISIBLE);
    ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
    //Changes the height and width to the specified *pixels*
    params.height = 0;
    params.width = 0;
    linearLayout.setLayoutParams(params);
  }

  private void initAjouterDepenseDialog(){
    activity = this;
    ajouterDepenseDialog = new AjouterDepenseDialog(activity);
    titre = ajouterDepenseDialog.getTitre();
    montant = ajouterDepenseDialog.getMontant();
    btnValider = ajouterDepenseDialog.getBtnValider();
    btnAnnuler = ajouterDepenseDialog.getBtnAnnuler();

    btnAnnuler.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ajouterDepenseDialog.dismiss();
      }
    });

    btnValider.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    ajouterDepenseDialog.build();

  }

  private void clickAjouterDepense(){
    btnAjouterDepense
      .setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          initAjouterDepenseDialog();
        }
      });
  }




}
