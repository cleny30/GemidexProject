using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace GemidexAPI.Model.Entity
{
    public class GemiType
    {
        [Key]
        [Required]
        public string TypeId { get; set; }
        public string TypeName { get; set; }
    }
}
