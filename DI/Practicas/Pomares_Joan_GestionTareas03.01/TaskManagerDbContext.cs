using System.Threading;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using Pomares_Joan_GestionTareas03._01;
namespace TaskManager.Data.Models;
public partial class TaskManagerDbContext : DbContext
{
    public virtual DbSet<User> Users { get; set; }
    public TaskManagerDbContext() { }
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB; Initial Catalog = Tareas_JPH");
    }
}
///<author> Joan Pomares Herrero</author>