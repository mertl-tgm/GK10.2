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

// Die Elementvorlage "Leere Seite" wird unter https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0x407 dokumentiert.

namespace GK10._2
{
    /// <summary>
    /// Eine leere Seite, die eigenständig verwendet oder zu der innerhalb eines Rahmens navigiert werden kann.
    /// </summary>
    public sealed partial class Register : Page
    {
        public Register()
        {
            this.InitializeComponent();
            GetRequest();
        }

        private void TitleTextBlock_SelectionChanged(object sender, RoutedEventArgs e)
        {

        }

        public async void GetRequest()
        {
            Uri geturi = new Uri("http://localhost:8080/ertl/register"); //replace your url  //Abfangen von Exception
            string response = "";
            try
            {
                System.Net.Http.HttpClient client = new System.Net.Http.HttpClient();
                System.Net.Http.HttpResponseMessage responseGet = await client.GetAsync(geturi);
                response = await responseGet.Content.ReadAsStringAsync();
            }
            catch (System.Net.Http.HttpRequestException)
            {
                response = "Fehler beim Verbinden mit den Server.";
            }
            
            System.Diagnostics.Debug.WriteLine(response);
            this.errormessages.NavigateToString(response);
        }

        private void ChangeToLogin(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            System.Diagnostics.Debug.WriteLine("Change to Login");
            System.Diagnostics.Debug.WriteLine("----------------------------------");

            Frame.Navigate(typeof(Login));

        }

        private void RegisterUser(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            System.Diagnostics.Debug.WriteLine("Registrieren");
            System.Diagnostics.Debug.WriteLine("----------------------------------");

            

        }

        private void Exit(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            System.Diagnostics.Debug.WriteLine("Beenden");
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            Application.Current.Exit();
        }
        private void HamburgerButton_Click(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            System.Diagnostics.Debug.WriteLine("Hamburger Menu");
            System.Diagnostics.Debug.WriteLine("----------------------------------");
            MySplitView.IsPaneOpen = !MySplitView.IsPaneOpen;
        }

    }
}
