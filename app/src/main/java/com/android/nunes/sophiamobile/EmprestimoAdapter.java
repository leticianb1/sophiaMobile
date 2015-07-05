package com.android.nunes.sophiamobile;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.nunes.sophiamobile.model.Emprestimo;

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

        emprestimosViewHolder.livro.setText(livro);
    }

    @Override
    public int getItemCount() {
        return emprestimos.size();
    }


    public class EmprestimosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context context;
        CardView cv;
        TextView livro;

        EmprestimosViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.card);
            livro = (TextView)itemView.findViewById(R.id.livro);

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
