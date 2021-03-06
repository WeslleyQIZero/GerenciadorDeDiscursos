package com.qi0.weslley.gerenciadordediscursos.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.qi0.weslley.gerenciadordediscursos.R;
import com.qi0.weslley.gerenciadordediscursos.activitys.AdicionarEditarActivity;
import com.qi0.weslley.gerenciadordediscursos.model.Congregacao;
import com.qi0.weslley.gerenciadordediscursos.model.Orador;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OradorAdaper extends RecyclerView.Adapter<OradorAdaper.MyViewHolder> {

    private List<Orador> oradores;
    List<Congregacao> congregacoes;
    private Context context;

    public OradorAdaper(List<Orador> oradores, List<Congregacao> congregacoes, Context context) {
        this.oradores = oradores;
        this.congregacoes = congregacoes;
        this.context = context;
    }

    public List<Orador> getOradores() {
        return this.oradores;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_orador, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Orador orador = oradores.get( position );
        holder.nomeOrador.setText( orador.getNome() );
        holder.congregacao.setText( pegarNomeDaCongregacao(orador.getIdCongregacao()) );
        holder.ultimaVisita.setText( orador.getUltimaVisita() );

        if( orador.getUrlFotoOrador() != null ){
            Uri uri = Uri.parse( orador.getUrlFotoOrador() );
            Glide.with( context )
                    .load( uri )
                    .error( R.drawable.img_padrao )
                    .into( holder.fotoOrador );
        }else {
            holder.fotoOrador.setImageResource( R.drawable.img_padrao );
        }
    }

    @Override
    public int getItemCount() {
        return oradores.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeOrador, congregacao, ultimaVisita;
        CircleImageView fotoOrador;
        //ImageView menuItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            nomeOrador = itemView.findViewById(R.id.tv_nome_orador);
            congregacao = itemView.findViewById(R.id.tv_orador_nome_congregacao);
            ultimaVisita = itemView.findViewById(R.id.tv_data_ultima_visita);
            fotoOrador = itemView.findViewById(R.id.item_orador_foto);
            //menuItem = itemView.findViewById(R.id.menu_item_recycle_view_orador);
        }

    }

    private String pegarNomeDaCongregacao(String idCongregacao){
        String nomeDaCongregacao = "";

        for (Congregacao congregacao : congregacoes){
            if (congregacao.getIdCongregacao() != null){
                if (congregacao.getIdCongregacao().equals(idCongregacao)){
                    nomeDaCongregacao = congregacao.getNomeCongregacao();
                }
            }

        }
        return nomeDaCongregacao;
    }
}
