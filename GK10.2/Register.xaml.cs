using System;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;
using Newtonsoft.Json;

// Die Elementvorlage "Leere Seite" wird unter https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0x407 dokumentiert.

namespace GK10._2
{
    /// <summary>
    /// Eine leere Seite, die eigenständig verwendet oder zu der innerhalb eines Rahmens navigiert werden kann.
    /// </summary>
    public sealed partial class Register : Page
    {
        private Param param;

        public Register()
        {
            this.InitializeComponent();
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);

            this.param = new Param();
            this.param.App = (App)e.Parameter;
        }

        private void ChangeToLogin(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(Login), this.param.App);
        }

        private async void RegisterUser(object sender, RoutedEventArgs e)
        {
            string param = "vname=" + this.vname.Text + "&nname=" + this.nname.Text + "&email=" + this.email.Text + "&pw=" + this.param.App.HashPW(this.pw.Password) + "&pwagain=" + this.param.App.HashPW(this.pwagain.Password);

            Uri geturi = new Uri("http://37.252.185.24:8080/ertl/register?" + param);
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
                this.errormessages.NavigateToString(response);

                this.param.Text = (string)result[1];
                Frame.Navigate(typeof(MainPage), this.param);
                return;
            }

            this.errormessages.NavigateToString("Fehler beim Verarbeiten der Antwort vom Server.");
        }

        private void Exit(object sender, RoutedEventArgs e)
        {
            Application.Current.Exit();
        }
        private void HamburgerButton_Click(object sender, RoutedEventArgs e)
        {
            MySplitView.IsPaneOpen = !MySplitView.IsPaneOpen;
        }

    }
}
