// Completa este archivo


const previewImagen = document.getElementById("image-preview");
const formImagen = document.getElementById("image")
formImagen.addEventListener("input", function(event)
{
    previewImagen.src = "";
    previewImagen.classList.add("hidden")
    let archivo = event.target.files[0];
    let reader = new FileReader();
    if (archivo) reader.readAsDataURL(archivo);
    reader.addEventListener("load", event => {
        previewImagen.src = reader.result;
        previewImagen.classList.remove("hidden");

    })
   
})