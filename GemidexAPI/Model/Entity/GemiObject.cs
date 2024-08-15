using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace GemidexAPI.Model.Entity
{
    public class GemiObject
    {
        [Key]
        [Required]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int GemiId {  get; set; }

        [Required]
        public string GoogleId { get; set; }

        public string TypeId { get; set; }

        public string SubTypeId { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string GemiName { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string Description { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string Weight { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string Heigh { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string Category { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string Speed { get; set; }

    }
}
