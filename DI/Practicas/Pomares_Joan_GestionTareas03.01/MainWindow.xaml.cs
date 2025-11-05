using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using Ejemplo_Usuarios_Acceso_Datos;
using Microsoft.EntityFrameworkCore;
using TaskManager.Data.Models;

namespace Pomares_Joan_GestionTareas03._01
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();

            string nombreUsuario = "admin";
            var service = new ServiceUser();

            anyadirUser(new User(0, "admin", PasswordHelper.HashPassword("1234"), "admin", "admin@admin.com", 1, DateTime.Now));

            LoginView loginView = new LoginView();
            this.Hide();
            loginView.Show();
        }
        async public void anyadirUser(User user)
        {
            var service = new ServiceUser();
            if(await buscarUser(user.Usuario) == null)
            {

                await service.Insertar(user);
            }
        }
        async public Task<User?> buscarUser(String nombre)
        {
            var service = new ServiceUser();
            var usuario = await service.Listar(nombre);

            if (usuario != null)
            {
                return usuario;
            }
            return null;
        }
    }
}
///<author> Joan Pomares Herrero</author>