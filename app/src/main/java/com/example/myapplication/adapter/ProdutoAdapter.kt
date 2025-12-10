package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Produto

class ProdutoAdapter(
    private val lista: ArrayList<Produto>,
    private val onClick: (Produto) -> Unit,
    private val onDelete: (Produto) -> Unit
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    class ProdutoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.txtNomeProduto)
        val preco: TextView = view.findViewById(R.id.txtPrecoProduto)
        val excluir: ImageView = view.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return ProdutoViewHolder(item)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = lista[position]
        holder.nome.text = produto.nome
        holder.preco.text = "R$ ${produto.preco}"

        holder.itemView.setOnClickListener { onClick(produto) }
        holder.excluir.setOnClickListener { onDelete(produto) }
    }

    override fun getItemCount() = lista.size
}
