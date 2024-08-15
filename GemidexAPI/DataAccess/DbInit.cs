using GemidexAPI.Model.Entity;

namespace GemidexAPI.DataAccess
{
    public class DbInit
    {
        public static void Initializer(GemidexContext context)
        {
            if (!context.GemiType.Any())
            {
                var types = new GemiType[]
                {
                    new GemiType{TypeId="T001", TypeName="normal"},
                    new GemiType{TypeId="T002", TypeName="fire"},
                    new GemiType{TypeId="T003", TypeName="water"},
                    new GemiType{TypeId="T004", TypeName="electric"},
                    new GemiType{TypeId="T005", TypeName="grass"},
                    new GemiType{TypeId="T006", TypeName="ice"},
                    new GemiType{TypeId="T007", TypeName="fighting"},
                    new GemiType{TypeId="T008", TypeName="poison"},
                    new GemiType{TypeId="T009", TypeName="ground"},
                    new GemiType{TypeId="T010", TypeName="flying"},
                    new GemiType{TypeId="T011", TypeName="psychic"},
                    new GemiType{TypeId="T012", TypeName="bug"},
                    new GemiType{TypeId="T013", TypeName="rock"},
                    new GemiType{TypeId="T014", TypeName="ghost"},
                    new GemiType{TypeId="T015", TypeName="dragon"},
                    new GemiType{TypeId="T016", TypeName="dark"},
                    new GemiType{TypeId="T017", TypeName="steel"},
                    new GemiType{TypeId="T018", TypeName="fairy"},
                    new GemiType{TypeId="T019", TypeName="inanime"},
                };

                context.GemiType.AddRange(types);
                context.SaveChanges();
            }
        }
    }
}
