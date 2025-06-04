const modal = document.getElementById("modal");
const btnAdd = document.getElementById("btn-add");
const btnCancelar = document.getElementById("cancelar");
const btnSalvar = document.getElementById("salvar");

btnAdd.addEventListener("click", () => {
  modal.style.display = "flex";
});

btnCancelar.addEventListener("click", fecharModal);

btnSalvar.addEventListener("click", () => {
  const nome = document.getElementById("nome").value.trim();
  const descricao = document.getElementById("descricao").value.trim();
  const preco = parseFloat(document.getElementById("preco").value);
  const estoque = parseInt(document.getElementById("estoque").value);

  if (!nome || !descricao || isNaN(preco) || isNaN(estoque)) {
    alert("Por favor, preencha todos os campos corretamente.");
    return;
  }

  const novoProduto = { nome, descricao, preco, estoque };
  console.log("Produto salvo:", novoProduto);

  // Aqui você pode fazer um fetch para API se quiser:
  // fetch('/api/produtos', {
  //   method: 'POST',
  //   headers: { 'Content-Type': 'application/json' },
  //   body: JSON.stringify(novoProduto)
  // })

  fecharModal();
});

function fecharModal() {
  modal.style.display = "none";
  limparCampos();
}

function limparCampos() {
  document.getElementById("nome").value = "";
  document.getElementById("descricao").value = "";
  document.getElementById("preco").value = "";
  document.getElementById("estoque").value = "";
}
