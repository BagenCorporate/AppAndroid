package fr.legrand.application.bagen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DepenseAdapter extends BaseAdapter {

  private final ArrayList<Depenses> lesDepenses ; // liste des frais du mois
  private final LayoutInflater inflater ;
  private Context context;
  private String key;
  //private Activity activity;


  /**
   * Constructeur de l'adapter pour valoriser les propriétés
   * @param context Accès au contexte de l'application
   * @param lesDepenses Liste des frais hors forfait
   */
  public DepenseAdapter(Context context, ArrayList<Depenses> lesDepenses, String key) {
    inflater = LayoutInflater.from(context) ;
    this.lesDepenses = lesDepenses ;
    this.context = context;
    this.key = key;
    //this.activity = activity;
  }

  /**
   * retourne le nombre d'éléments de la listview
   */
  @Override
  public int getCount() {
    return lesDepenses.size() ;
  }

  /**
   * retourne l'item de la listview à un index précis
   */
  @Override
  public Object getItem(int index) {
    return lesDepenses.get(index) ;
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
    TextView txtlLibelleDepense ;
    TextView txtDateDepense;
    TextView txtMontantDepenseAdapter;
    //Button btnVoir;

  }

  /**
   * Affichage dans la liste
   */
  @Override
  public View getView(int index, View convertView, ViewGroup parent) {
    DepenseAdapter.ViewHolder holder ;
    if (convertView == null) {
      holder = new DepenseAdapter.ViewHolder() ;
      convertView = inflater.inflate(R.layout.layout_liste_depense, parent, false) ;
      holder.txtlLibelleDepense = convertView.findViewById(R.id.txtLibelleDepense);
      holder.txtDateDepense = convertView.findViewById(R.id.txtDateDepense);
      holder.txtMontantDepenseAdapter = convertView.findViewById(R.id.txtMontantDepenseAdapter);

      convertView.setTag(holder) ;
    }else{
      holder = (DepenseAdapter.ViewHolder)convertView.getTag();
    }
    holder.txtlLibelleDepense.setText(lesDepenses.get(index).getLibelle());
    holder.txtDateDepense.setText(lesDepenses.get(index).getDate());
    holder.txtMontantDepenseAdapter.setText(lesDepenses.get(index).getMontant()+" €");



    // gestion de l'événement clic sur le bouton de suppression

    return convertView ;
  }

}
