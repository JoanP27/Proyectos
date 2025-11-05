using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using Ejemplo_Usuarios_Acceso_Datos;

namespace Pomares_Joan_GestionTareas03._01
{

    /// <summary>
    /// Lógica de interacción para LoginView.xaml
    /// </summary>
    public partial class LoginView : Window
    {
        public LoginView()
        {
            InitializeComponent();
        }
        // Hara que se permita mover la ventana cuando el usuario mantenga click izquierdo en la pantalla
        private void Window_MouseDown(object sender, MouseButtonEventArgs e)
        {
            if(e.LeftButton==MouseButtonState.Pressed)
            {
                DragMove();
            }
        }
        // Se minimizara la ventana al presionarse el boton de minimizar
        private void btnMinimize_Click(object sender, RoutedEventArgs e)
        {
            WindowState=WindowState.Minimized;
        }
        // Se cerrara la ventana al presionar el boton de cerrar
        private void btnClose_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.Shutdown();
        }
        // Se llama la funcion login al hacer click en el boton de login
        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            login();
        }
        // Se recojen los datos de los bloques de texto y se usan
        async public void login()
        {
            String nombre = txtUser.Text;
            String password = PasswordHelper.HashPassword(txtPass.Password);
            User? usuario = await buscarUser(nombre);
            if (usuario != null && usuario.PasswordHash == password)
            {
                this.Hide();
                // Al saber que la aplicacion principal es main window, puedo llamar a la ventana y mostrarla de vuelta
                Application.Current.MainWindow.Show();
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
