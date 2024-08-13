using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace GemidexAPI.Model.Entity
{
    public class GemiObject
    {
        [Key]
        [Required]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int gemiID {  get; set; }

        [Required]
        public int UserId { get; set; }

        [Required]
        public string typeId { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string gemiName { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string description { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string weight { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string heigh { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string category { get; set; }

        [Required]
        [Column(TypeName = "varchar(MAX)")]
        public string speed { get; set; }

    }
}
