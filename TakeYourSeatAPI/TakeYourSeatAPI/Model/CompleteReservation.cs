using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TakeYourSeatAPI.Model
{
    public class CompleteReservation
    {
        public string Reservation { get; set; }
        public List<string> TableIds { get; set; }
        public List<string> FriendIds { get; set; }
    }
}