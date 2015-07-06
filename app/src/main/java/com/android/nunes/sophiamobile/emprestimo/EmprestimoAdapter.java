package com.android.nunes.sophiamobile.emprestimo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nunes.sophiamobile.R;
import com.android.nunes.sophiamobile.model.Emprestimo;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

/**
 * Created by Letícia on 28/06/2015.
 */
public class EmprestimoAdapter  extends RecyclerView.Adapter<EmprestimoAdapter.EmprestimosViewHolder> {

    private List<Emprestimo> emprestimos;
    private  ClickListener clickListener;


    public EmprestimoAdapter(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public EmprestimosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_emprestimo, viewGroup, false);
        EmprestimosViewHolder evholder = new EmprestimosViewHolder(v);
        return evholder;
    }

    @Override
    public void onBindViewHolder(EmprestimosViewHolder emprestimosViewHolder, int position) {
        String livro = emprestimos.get(position).getLivro();
        String dataDevolucao = emprestimos.get(position).getDataDevolucao();
        String imagem = emprestimos.get(position).getImagemLivro();

        emprestimosViewHolder.livro.setText(livro);
        emprestimosViewHolder.dataDevolucao.setText(dataDevolucao);
        UrlImageViewHelper.setUrlDrawable(emprestimosViewHolder.imagem, emprestimos.get(position).getImagemLivro());
    }

    @Override
    public int getItemCount() {
        return emprestimos.size();
    }


    public class EmprestimosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context context;
        CardView cv;
        TextView dataDevolucao;
        TextView livro;
        ImageView imagem;

        EmprestimosViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.card);
            livro = (TextView)itemView.findViewById(R.id.livro);
            imagem = (ImageView)itemView.findViewById(R.id.imagemLivro);

        }

        @Override
        public void onClick(View v) {

            if (clickListener !=null){
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }

    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }
}
