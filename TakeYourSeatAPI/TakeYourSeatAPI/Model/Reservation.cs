using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TakeYourSeatAPI.Model
{
    public class Reservation
    {
        public string StartDate { get; set; }
        public string EndDate { get; set; }
        public int User { get; set; }
        public int RestaurantId { get; set; }
    }
}