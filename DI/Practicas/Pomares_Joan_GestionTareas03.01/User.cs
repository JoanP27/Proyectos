using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pomares_Joan_GestionTareas03._01
{
    public class User
    {
        [Key]
        public int Id { get; set; }
        [Required]
        [MaxLength(30)]
        public string Usuario { get; set; } = string.Empty;
        [Required]
        public string PasswordHash { get; set; } = string.Empty;
        [MaxLength(100)]
        public string? NombreCompleto { get; set; }
        [Required]
        [MaxLength(100)]
        public string? CorreoElectronico { get; set; } = string.Empty;
        [Range(0,1)]
        public int Activo { get; set; } = 0;
        public DateTime FechaCreacion { get; set; } = DateTime.UtcNow;
        public DateTime FechaBaja {  get; set; } = DateTime.UtcNow;

        public User(int id, string usuario, string passwordHash, string? nombreCompleto, string? correoElectronico, int activo, DateTime fechaBaja)
        {
            Id = id;
            Usuario = usuario;
            PasswordHash = passwordHash;
            NombreCompleto = nombreCompleto;
            CorreoElectronico = correoElectronico;
            Activo = activo;
            FechaCreacion = DateTime.Now;
            FechaBaja = fechaBaja;
        }
    }
}
///<author> Joan Pomares Herrero</author>