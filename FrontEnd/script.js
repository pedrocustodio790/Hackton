const apiUrl = "http://localhost:8080/api/produtos";

document.addEventListener("DOMContentLoaded", listarProdutos);
document.getElementById("btn-add").onclick = abrirModal;

function listarProdutos() {
  fetch(apiUrl)
    .then(res => res.json())
    .then(data => {
      const container = document.getElementById("produtos");
      container.innerHTML = "";
      data.forEach(prod => {
        container.innerHTML += `
          <div class="card">
            <h3>${prod.nome}</h3>
            <p>${prod.descricao}</p>
            <p><strong>R$ ${prod.preco.toFixed(2)}</strong></p>
            <p>Estoque: ${prod.estoque}</p>
            <button onclick="atualizarEstoque(${prod.id})">📝 Atualizar Estoque</button>
            <button onclick="removerProduto(${prod.id})">❌ Remover</button>
          </div>
        `;
      });
    });
}

function abrirModal() {
  document.getElementById("modal").classList.remove("hidden");
}

function fecharModal() {
  document.getElementById("modal").classList.add("hidden");
}

function salvarProduto() {
  const produto = {
    nome: document.getElementById("nome").value,
    descricao: document.getElementById("descricao").value,
    preco: parseFloat(document.getElementById("preco").value),
    estoque: parseInt(document.getElementById("estoque").value),
  };

  fetch(apiUrl, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(produto)
  }).then(() => {
    fecharModal();
    listarProdutos();
  });
}

function atualizarEstoque(id) {
  const novoEstoque = prompt("Novo valor de estoque:");
  fetch(`${apiUrl}/${id}/estoque`, {
    method: "PUT",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({estoque: parseInt(novoEstoque)})
  }).then(listarProdutos);
}

function removerProduto(id) {
  if (confirm("Deseja remover este produto?")) {
    fetch(`${apiUrl}/${id}`, {method: "DELETE"})
      .then(listarProdutos);
  }
}
