const a: string = "Hola";

const array: Array<string> = [];

// Tipado de funciones
// Auto completa parametros
function suma(n1: number, n2: number): number {
  return n1 + n2;
}
console.log(suma(1, 2));
type Operacion = (n1: number, n2: number) => number;
function operar(n1: number, n2: number, f : Operacion)
{
    console.log(f(n1, n2))
}

console.log(1, 2, (n1: number, n2: number) => n1 + n2)


function longitud(valor : string | number) : number
{
    return valor.toString().length
}
