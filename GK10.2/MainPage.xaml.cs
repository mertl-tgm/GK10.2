using Newtonsoft.Json;
using System;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace GK10._2
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        private string emailAdr; 

        public MainPage()
        {
            this.InitializeComponent();
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            string param = (string)e.Parameter;
            string[] words = param.Split('#');

            this.vname.Text = "Vorname: " + words[0];
            this.nname.Text = "Nachname: " + words[1];
            this.email.Text = "E-Mail-Adresse: " + words[2];

            this.emailAdr = words[2];
        }

        private void HamburgerButton_Click(object sender, RoutedEventArgs e)
        {
            MySplitView.IsPaneOpen = !MySplitView.IsPaneOpen;
        }

        private async void DeleteUser(object sender, RoutedEventArgs e)
        {
            string param = "email=" + this.emailAdr;

            Uri geturi = new Uri("http://37.252.185.24:8080/ertl/delete?" + param);
            string response = "";
            try
            {
                System.Net.Http.HttpClient client = new System.Net.Http.HttpClient();
                System.Net.Http.HttpResponseMessage responseGet = await client.GetAsync(geturi);
                response = await responseGet.Content.ReadAsStringAsync();
            }
            catch (System.Net.Http.HttpRequestException)
            {
                this.errormessages.NavigateToString("Fehler beim Verbinden mit den Server.");
                return;
            }

            Newtonsoft.Json.Linq.JArray result = (Newtonsoft.Json.Linq.JArray)JsonConvert.DeserializeObject(response);
            if (((string)result[0]).Equals("error"))
            {
                this.errormessages.NavigateToString((string)result[1]);
                return;
            }
            else if (((string)result[0]).Equals("success"))
            {
                Frame.Navigate(typeof(Login));
                return;
            }

            this.errormessages.NavigateToString("Fehler beim Verarbeiten der Antwort vom Server.");
        }

        private void Exit(object sender, RoutedEventArgs e)
        {
            Application.Current.Exit();
        }

        private void Logout(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(Login));
        }
    }
}
