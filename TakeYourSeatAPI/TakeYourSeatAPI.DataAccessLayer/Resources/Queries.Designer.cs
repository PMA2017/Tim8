﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace TakeYourSeatAPI.DataAccessLayer.Resources {
    using System;
    
    
    /// <summary>
    ///   A strongly-typed resource class, for looking up localized strings, etc.
    /// </summary>
    // This class was auto-generated by the StronglyTypedResourceBuilder
    // class via a tool like ResGen or Visual Studio.
    // To add or remove a member, edit your .ResX file then rerun ResGen
    // with the /str option, or rebuild your VS project.
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("System.Resources.Tools.StronglyTypedResourceBuilder", "4.0.0.0")]
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
    [global::System.Runtime.CompilerServices.CompilerGeneratedAttribute()]
    internal class Queries {
        
        private static global::System.Resources.ResourceManager resourceMan;
        
        private static global::System.Globalization.CultureInfo resourceCulture;
        
        [global::System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1811:AvoidUncalledPrivateCode")]
        internal Queries() {
        }
        
        /// <summary>
        ///   Returns the cached ResourceManager instance used by this class.
        /// </summary>
        [global::System.ComponentModel.EditorBrowsableAttribute(global::System.ComponentModel.EditorBrowsableState.Advanced)]
        internal static global::System.Resources.ResourceManager ResourceManager {
            get {
                if (object.ReferenceEquals(resourceMan, null)) {
                    global::System.Resources.ResourceManager temp = new global::System.Resources.ResourceManager("TakeYourSeatAPI.DataAccessLayer.Resources.Queries", typeof(Queries).Assembly);
                    resourceMan = temp;
                }
                return resourceMan;
            }
        }
        
        /// <summary>
        ///   Overrides the current thread's CurrentUICulture property for all
        ///   resource lookups using this strongly typed resource class.
        /// </summary>
        [global::System.ComponentModel.EditorBrowsableAttribute(global::System.ComponentModel.EditorBrowsableState.Advanced)]
        internal static global::System.Globalization.CultureInfo Culture {
            get {
                return resourceCulture;
            }
            set {
                resourceCulture = value;
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to DELETE FROM [{0}] WHERE [{1}] = &apos;{2}&apos;.
        /// </summary>
        internal static string Delete {
            get {
                return ResourceManager.GetString("Delete", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to DELETE FROM [TakeYourSeatDB].[dbo].[Friends] WHERE ([User] = {0} AND [Friend] = {1}) OR ([User] = {1} AND [Friend] = {0}).
        /// </summary>
        internal static string DeleteFriends {
            get {
                return ResourceManager.GetString("DeleteFriends", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to INSERT INTO [{0}] ({1}) VALUES ({2}); SELECT SCOPE_IDENTITY();.
        /// </summary>
        internal static string Insert {
            get {
                return ResourceManager.GetString("Insert", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to SELECT {0} FROM [{1}].
        /// </summary>
        internal static string SelectAll {
            get {
                return ResourceManager.GetString("SelectAll", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to SELECT COLUMN_NAME
        ///FROM TakeYourSeatDB.INFORMATION_SCHEMA.COLUMNS
        ///WHERE TABLE_NAME = &apos;{0}&apos;.
        /// </summary>
        internal static string SelectColumnNames {
            get {
                return ResourceManager.GetString("SelectColumnNames", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to SELECT rt.Id, rt.Number, res.StartDate 
        ///FROM Reservation res
        ///INNER JOIN TableReservation tr ON res.Id = tr.ReservationId
        ///INNER JOIN RestaurantTable rt ON rt.Id = tr.TableId
        ///WHERE rt.Restaurant = {0}.
        /// </summary>
        internal static string SelectReservationByRestaurant {
            get {
                return ResourceManager.GetString("SelectReservationByRestaurant", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to select r.Id, r.Name, r.Description, r.Image, r.Phone, r.Email, r.Webste, r.Address, r.Location, l.Latitude, l.Longitude from Restaurant r
        ///join Location l
        ///on r.Location = l.Id.
        /// </summary>
        internal static string SelectRestaurantsWithLocation {
            get {
                return ResourceManager.GetString("SelectRestaurantsWithLocation", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to SELECT {0} FROM [{1}] WHERE [{2}] = &apos;{3}&apos;.
        /// </summary>
        internal static string SelectWhere {
            get {
                return ResourceManager.GetString("SelectWhere", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to SELECT {0} FROM [{1}] WHERE [{2}] IN ({3}).
        /// </summary>
        internal static string SelectWhereIn {
            get {
                return ResourceManager.GetString("SelectWhereIn", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to SELECT {0} FROM [{1}] WHERE [{2}] NOT IN ({3}).
        /// </summary>
        internal static string SelectWhereNotIn {
            get {
                return ResourceManager.GetString("SelectWhereNotIn", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to UPDATE [{0}] SET {1} WHERE [{2}] = &apos;{3}&apos;.
        /// </summary>
        internal static string Update {
            get {
                return ResourceManager.GetString("Update", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to UPDATE ReservationFriends SET Status = &apos;1&apos; WHERE ReservationId = &apos;{0}&apos; AND UserId = &apos;{1}&apos;.
        /// </summary>
        internal static string UpdateInvitationStatusQuery {
            get {
                return ResourceManager.GetString("UpdateInvitationStatusQuery", resourceCulture);
            }
        }
    }
}
