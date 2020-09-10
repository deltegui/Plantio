using Microsoft.EntityFrameworkCore.Migrations;

namespace plantio.Migrations
{
    public partial class tokens : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Tokens",
                columns: table => new
                {
                    Value = table.Column<string>(nullable: false),
                    OwnerName = table.Column<string>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Tokens", x => x.Value);
                    table.ForeignKey(
                        name: "FK_Tokens_Users_OwnerName",
                        column: x => x.OwnerName,
                        principalTable: "Users",
                        principalColumn: "Name",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Tokens_OwnerName",
                table: "Tokens",
                column: "OwnerName");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Tokens");
        }
    }
}
