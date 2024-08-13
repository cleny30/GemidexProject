using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

public class User
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int UserId { get; set; }

    [Required]
    [MaxLength(255)]
    public string Email { get; set; }

    [MaxLength(255)]
    public string FullName { get; set; }

    [MaxLength(255)]
    public string GoogleId { get; set; }
}
