<template>
  <div class="mx-auto flex max-w-screen-xl flex-col flex-wrap p-4">
    <h1 class="border-b-2 p-4 pb-1 text-4xl font-medium text-white">OFERTA</h1>
    <ul class="grid grid-cols-1 gap-7 py-7 sm:grid-cols-4 md:grid-cols-4">
      <li
        v-for="item in items"
        :key="item.name"
        class="flex flex-col items-center justify-center rounded-xl bg-light-gray"
      >
        <img :src="item.source" alt="" />
        <!-- <img src="@/assets/White_GT3_RS_sticker_side_profile_with_traffic_lights.jpg" alt="" /> -->
        <router-link :to="item.url" class="mx-auto p-4">
          <span class="p-4 font-medium text-white">{{ item.name }} </span>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'MenuSection',
  data() {
    return {
      items: [
        {
          name: 'Mycie Ręczne',
          url: '#',
          source: 'src/assets/manualwash/foamed_porsche.jpg'
        },
        { name: 'Korekta Lakieru', url: '#', source: 'src/assets/porsche_polishing.jpg' },
        { name: 'Woskowanie', url: '#', source: 'src/assets/waxing.jpg' },
        {
          name: 'Zabezpieczenie PPF',
          url: '#',
          source: 'src/assets/ppf/car_hood_ppf.jpg'
        },
        {
          name: 'Powłoka Ceramiczna',
          url: '#',
          source: 'src/assets/porsche_ceramic_coating.jpg'
        },
        {
          name: 'Czyszczenie Wnętrza',
          url: '#',
          source: 'src/assets/porsche_interior_cleaning.jpg'
        },
        {
          name: 'Przyciemnianie Szyb',
          url: '#',
          source: 'src/assets/window_tint.jpg'
        }
      ]
    }
  }
}
</script>
