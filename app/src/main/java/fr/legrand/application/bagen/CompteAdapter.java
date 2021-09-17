package fr.legrand.application.bagen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class CompteAdapter extends BaseAdapter {

  private final ArrayList<Compte> lesComptes ; // liste des frais du mois
  private final LayoutInflater inflater ;
  private Context context;
  private String key;
  //private Activity activity;


  /**
   * Constructeur de l'adapter pour valoriser les propriétés
   * @param context Accès au contexte de l'application
   * @param lesComptes Liste des frais hors forfait
   */
  public CompteAdapter(Context context, ArrayList<Compte> lesComptes, String key) {
    inflater = LayoutInflater.from(context) ;
    this.lesComptes = lesComptes ;
    this.context = context;
    this.key = key;
    //this.activity = activity;
  }

  /**
   * retourne le nombre d'éléments de la listview
   */
  @Override
  public int getCount() {
    return lesComptes.size() ;
  }

  /**
   * retourne l'item de la listview à un index précis
   */
  @Override
  public Object getItem(int index) {
    return lesComptes.get(index) ;
  }

  /**
   * retourne l'index de l'élément actuel
   */
  @Override
  public long getItemId(int index) {
    return index;
  }

  /**
   * structure contenant les éléments d'une ligne
   */
  private class ViewHolder {
    TextView txtIntitule ;
    TextView txtBudget;
    TextView txtDepense;
    Button btnVoir;

  }

  /**
   * Affichage dans la liste
   */
  @Override
  public View getView(int index, View convertView, ViewGroup parent) {
    ViewHolder holder ;
    if (convertView == null) {
      holder = new ViewHolder() ;
      convertView = inflater.inflate(R.layout.layout_liste_compte, parent, false) ;
      holder.txtIntitule = convertView.findViewById(R.id.txtIntitule);
      holder.txtDepense = convertView.findViewById(R.id.txtDepense);
      holder.txtBudget = convertView.findViewById(R.id.txtBudget);
      holder.btnVoir = convertView.findViewById(R.id.btnVoir);
      convertView.setTag(holder) ;
    }else{
      holder = (ViewHolder)convertView.getTag();
    }
    holder.txtIntitule.setText(lesComptes.get(index).getIntitule()+" €");
    holder.txtBudget.setText(lesComptes.get(index).getLeBudget().getMontant()+" €");
    holder.txtDepense.setText(lesComptes.get(index).getTotalDepense()+" €");
    holder.btnVoir.setTag(lesComptes.get(index).getId());
    holder.btnVoir.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // on récupère l'index
        String index = (String)v.getTag();
        Serializer.serialize("numCompte",index,context);
        // on actualise la view
        Intent intent = new Intent(context, compteDetail.class) ;
        context.startActivity(intent) ;

        notifyDataSetChanged() ;

      }
    });

    // gestion de l'événement clic sur le bouton de suppression

    return convertView ;
  }

}
