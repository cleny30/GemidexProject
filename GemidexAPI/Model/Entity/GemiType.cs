using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace GemidexAPI.Model.Entity
{
    public class GemiType
    {
        [Key]
        [Required]

        public string typeId { get; set; }
        public string background { get; set; }

    }
}
