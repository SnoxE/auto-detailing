<template>
  <div class="bg-dark-mode-gray p-10">
    <ThreeJsAnimation></ThreeJsAnimation>
    <div class="mx-auto flex max-w-screen-xl flex-wrap">
      <h1 class="border-b-2 pb-1 text-4xl font-medium text-white">O NAS</h1>
      <div class="mx-auto flex justify-center gap-10">
        <span class="mt-4 hidden justify-center text-lg text-white sm:flex">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Rerum illum optio alias accusamus
          magnam iure error minus corporis dolores provident, odit maxime obcaecati iusto impedit
          repudiandae voluptas, sapiente dolorem saepe?
        </span>
        <span class="mt-4 hidden text-lg text-white sm:flex">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Rerum illum optio alias accusamus
          magnam iure error minus corporis dolores provident, odit maxime obcaecati iusto impedit
          repudiandae voluptas, sapiente dolorem saepe?
        </span>
        <span class="mt-4 text-lg text-white sm:hidden">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Rerum illum optio alias accusamus
          magnam iure error minus corporis dolores provident, odit maxime obcaecati iusto impedit
          repudiandae voluptas, sapiente dolorem saepe?
        </span>
      </div>
    </div>
  </div>
  <div class="flex flex-col bg-dark-mode-gray py-16 md:flex-row">
    <img
      src="@/assets/White_GT3_RS_sticker_side_profile_with_traffic_lights.jpg"
      alt=""
      class="justify-start md:w-1/2"
    />
    <div class="flex flex-col justify-center gap-6 px-10 py-4">
      <!-- <div class="mx-auto flex max-w-screen-xl flex-col flex-wrap gap-6"> -->
      <h2 class="border-b-2 pb-1 text-4xl font-medium text-white">KONTAKT</h2>
      <span class="text-lg text-white">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Officia dolor in accusamus, maxime
        facere voluptate est vel quaerat veritatis sunt asperiores, repellendus deserunt labore
        reiciendis ipsam hic aut nulla necessitatibus.
      </span>
    </div>
  </div>
</template>

<script>
import ThreeJsAnimation from '@/components/ThreeJsAnimation.vue'

export default {
  name: 'AboutSection',
  components: {
    ThreeJsAnimation
  }
}
</script>
