using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace GK10._2
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class Login : Page
    {
        public Login()
        {
            this.InitializeComponent();
        }

        private void HamburgerButton_Click(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            System.Diagnostics.Debug.WriteLine("Hamburger Menu");
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            MySplitView.IsPaneOpen = !MySplitView.IsPaneOpen;
        }

        private void Exit(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            System.Diagnostics.Debug.WriteLine("Beenden");
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            Application.Current.Exit();
        }

        private void ChangeToRegister(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            System.Diagnostics.Debug.WriteLine("Change to Register");
            System.Diagnostics.Debug.WriteLine("----------------------------------");

            Frame.Navigate(typeof(Register));

        }

        private void LoginUser(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            System.Diagnostics.Debug.WriteLine("Login");
            System.Diagnostics.Debug.WriteLine("----------------------------------");

            Frame.Navigate(typeof(Login));

        }
    }
}
