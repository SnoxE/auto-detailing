<template>
  <div
    class="relative w-full justify-center before:absolute before:inset-0 before:bg-black before:opacity-60 before:content-['']"
  >
    <picture class="w-full">
      <source media="(max-width: 714px)" srcset="@/assets/Taycan_wash_edited_mobile.jpg" />
      <source media="(min-width: 715px)" srcset="@/assets/Taycan_wash_edited.jpg" />
      <img
        src="@/assets/Taycan_wash_edited.jpg"
        alt="Chris standing up holding his daughter Elva"
      />
    </picture>

    <div
      class="z-5 absolute top-1/2 flex w-full -translate-y-1/2 transform flex-col justify-center"
    >
      <h1
        class="flex justify-center p-4 text-center text-2xl font-bold text-white md:text-3xl lg:text-6xl"
      >
        Perfekcja w każdym detalu. <br />
        Twoje auto, nasza pasja.
      </h1>
      <div class="mt-8 flex w-full justify-center">
        <router-link
          to="/oferta"
          class="prices-btn mx-4 my-2 flex w-40 justify-center rounded-lg border border-solid border-mcl-orange bg-transparent px-6 py-2 font-medium text-white hover:bg-mcl-orange"
        >
          Nasza oferta
        </router-link>
        <router-link
          to="/rezerwuj"
          class="prices-btn mx-4 my-2 flex w-40 justify-center rounded-lg border border-solid border-mcl-orange bg-transparent px-6 py-2 font-medium text-white hover:bg-mcl-orange"
        >
          Rezerwuj
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeroSection'
}
</script>
