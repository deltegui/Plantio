﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using plantio.Model;

namespace plantio.Migrations
{
    [DbContext(typeof(PlantioContext))]
    partial class PlantioContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "3.1.8")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

            modelBuilder.Entity("plantio.Domain.User", b =>
                {
                    b.Property<string>("Name")
                        .HasColumnType("varchar(767)");

                    b.Property<DateTime>("LastConnection")
                        .HasColumnType("datetime");

                    b.Property<string>("Password")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("Name");

                    b.ToTable("Users");
                });
#pragma warning restore 612, 618
        }
    }
}