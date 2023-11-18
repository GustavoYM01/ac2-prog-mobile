package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_mobile.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    // Lista de itens a serem exibidos na RecyclerView.
    private List<ItemModel> itemList;

    // Contexto da atividade que está usando o adaptador.
    private Context context;

    // Construtor do adaptador.
    public ItemAdapter(Context context, List<ItemModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // Método chamado quando a RecyclerView precisa criar um novo ViewHolder.
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla (converte) o layout XML do item em uma View.
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);

        // Retorna um novo ViewHolder associado à View inflada.
        return new ItemViewHolder(view);
    }

    // Método chamado para associar os dados de um item a um ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Obtém o objeto ItemModel correspondente à posição na lista.
        ItemModel item = itemList.get(position);

        // Define os dados do item nos elementos de interface do ViewHolder.
        holder.nomeTextView.setText(item.getNome());
        holder.descricaoTextView.setText(item.getDescricao());

        // Adiciona o evento OnClickListener ao botão de exclusão.
        holder.btnDelete.setOnClickListener(v -> {
            // Remove o item da lista.
            itemList.remove(position);
            // Remove o item do banco de dados.
            DatabaseReference itensRef = FirebaseDatabase.getInstance().getReference("itens");
            itensRef.child(String.valueOf(item.getNome())).removeValue();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // Elementos de interface no layout do item.
        TextView nomeTextView;
        TextView descricaoTextView;
        Button btnDelete;

        // Construtor do ViewHolder.
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            // Associa os elementos de interface às variáveis do ViewHolder.
            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            descricaoTextView = itemView.findViewById(R.id.descricaoTextView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
