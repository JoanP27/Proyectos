renderItem()
function renderItem(dato)
{
    const template = document.getElementById("browser-item-template");
    const item = template.content.cloneNode(true).firstElementChild;
    const container = document.getElementById("item-container");
    item.id = "1"
    item.children[0].src = "resources/Papirus-Dark/128x128/folder-black-documents.svg";
    item.children[1].innerText = "Documentos"
    container.append(item)
}

for(i=0;i<10;i++)
{
    renderItem();
}